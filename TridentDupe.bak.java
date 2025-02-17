/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package com.example.addon.modules;

import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.events.game.GameLeftEvent;
import meteordevelopment.meteorclient.events.game.OpenScreenEvent;
import meteordevelopment.meteorclient.events.packets.PacketEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.DoubleSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.orbit.EventPriority;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.network.packet.c2s.play.*;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.*;

public class TridentDupe extends Module {
    // Coded by Killet Laztec & Ionar :3
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final Setting<Double> delay = sgGeneral.add(new DoubleSetting.Builder()
        .name("dupe-delay")
        .description("Raise this if it isn't working. This is how fast you'll dupe. 5 is good for most.")
        .defaultValue(5)
        .build()
    );

    public TridentDupe() {
        super(com.example.addon.TridentDupe.CATEGORY, "trident-dupe", "Dupes tridents in first hotbar slot. / / Killet / / Laztec / / Ionar");
    }

    private boolean banClickSlotAndPlayerActionPacket;
    @EventHandler(priority = EventPriority.HIGHEST + 1)
    private void onSendPacket(PacketEvent.Send event) {
        if (event.packet instanceof ClientTickEndC2SPacket
            || event.packet instanceof PlayerMoveC2SPacket
            || event.packet instanceof CloseHandledScreenC2SPacket)
            return;

        if (!(event.packet instanceof ClickSlotC2SPacket)
            && !(event.packet instanceof PlayerActionC2SPacket))
        {
            return;
        }
        if (!banClickSlotAndPlayerActionPacket)
            return;
        event.cancel();
    }

    @Override
    public void onActivate()
    {
        if (mc.player == null)
            return;
        scheduledTasks.clear();
        dupe();
    }

    private void dupe()
    {
        int delayInt = (delay.get()).intValue()*100;

        // 先右键交互点击一下主手物品
        // 即告诉服务端“我开始右键长按了，三叉戟该开始瞄准了”
        mc.interactionManager.interactItem(mc.player, Hand.MAIN_HAND);
        banClickSlotAndPlayerActionPacket = true;

        // 然后使用 定时器1 延时 500ms
        scheduleTask(() -> {
            banClickSlotAndPlayerActionPacket = false;
            // 物品栏 左键点击 3（合成格子左下角那一格）
            // 重点在于这里，SWAP 在 Bukkit 接口称为 HOTBAR_SWAP
            // 也就是将这个格子的物品与快捷栏的物品进行交换
            // 这是 Bukkit 可以监听到的 InventoryClickEvent，所以很容易解决它
            // 只要物品没有移动成功，就刷不了
            mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId,
                3, 0, SlotActionType.SWAP, mc.player);

            // 发一个包 玩家动作 RELEASE USE ITEM
            // 即告诉服务端“我松开鼠标了，要发射三叉戟了”
            PlayerActionC2SPacket packet2 = new PlayerActionC2SPacket(
                PlayerActionC2SPacket.Action.RELEASE_USE_ITEM,
                BlockPos.ORIGIN,
                Direction.DOWN,
                0);
            mc.getNetworkHandler().sendPacket(packet2);

            banClickSlotAndPlayerActionPacket = true;
            // 用 定时器2 延时 500ms，再来一次
            scheduleTask2(this::dupe, delayInt);
        }, delayInt);
    }

    private final List<Pair<Long, Runnable>> scheduledTasks = new ArrayList<>();
    private final List<Pair<Long, Runnable>> scheduledTasks2 = new ArrayList<>();

    public void scheduleTask(Runnable task, long delayMillis) {
        long executeTime = System.currentTimeMillis() + delayMillis;
        scheduledTasks.add(new Pair<>(executeTime, task));
    }
    public void scheduleTask2(Runnable task, long delayMillis) {
        long executeTime = System.currentTimeMillis() + delayMillis;
        scheduledTasks2.add(new Pair<>(executeTime, task));
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        long currentTime = System.currentTimeMillis();
        {
            Iterator<Pair<Long, Runnable>> iterator = scheduledTasks.iterator();

            while (iterator.hasNext()) {
                Pair<Long, Runnable> entry = iterator.next();
                if (entry.getLeft() <= currentTime) {
                    entry.getRight().run();
                    iterator.remove(); // Remove executed task from the list
                }
            }
        }
        {
            Iterator<Pair<Long, Runnable>> iterator = scheduledTasks2.iterator();

            while (iterator.hasNext()) {
                Pair<Long, Runnable> entry = iterator.next();
                if (entry.getLeft() <= currentTime) {
                    entry.getRight().run();
                    iterator.remove(); // Remove executed task from the list
                }
            }
        }
    }

    @EventHandler
    private void onGameLeft(GameLeftEvent event) {
        toggle();
    }

    @EventHandler
    private void onScreenOpen(OpenScreenEvent event) {
        if (event.screen instanceof DisconnectedScreen) {
            toggle();
        }
    }

}
