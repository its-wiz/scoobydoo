package org.itsrune.scoobydoo;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.itsrune.scoobydoo.Checks.Movement.SpeedA;
import org.itsrune.scoobydoo.Checks.Movement.SpeedB;

import java.util.HashMap;

public final class Scoobydoo extends JavaPlugin implements Listener {

    public static String PLUGIN_COLOR = "§e";
    public static String PLUGIN_MESSAGE = "§7[" + PLUGIN_COLOR + "scoobydoo§7] " + PLUGIN_COLOR;
    public static HashMap<Player, Integer> PlayerViolationLevel = new HashMap<>();
    static SpeedA SpeedADetection = new SpeedA();
    static SpeedB AirMoveADetection = new SpeedB();


//    static KillauraA KillauraADetection = new KillauraA();

    public static void FlagPlayer(Player player, String checkType, String debugMessage){
        if (!(PlayerViolationLevel.containsKey(player))){
            PlayerViolationLevel.put(player, 0);
        }
        PlayerViolationLevel.put(player, PlayerViolationLevel.get(player) + 1);
        Bukkit.broadcastMessage("§7[§eAnticheat§7] " + PLUGIN_COLOR + player.getName() + " §7has failed " + PLUGIN_COLOR + checkType + " §7[VL: " + PLUGIN_COLOR + PlayerViolationLevel.get(player) + "§7]");
        Bukkit.broadcastMessage("§7Debug: §f" + debugMessage);
    }


    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this,this);
//        ProtocolManager manager = ProtocolLibrary.getProtocolManager();
        Bukkit.broadcastMessage(PLUGIN_MESSAGE + "successfully loaded");

//        manager.addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL, PacketType.Play.Client.POSITION_LOOK) {
//            @Override
//            public void onPacketReceiving(PacketEvent event) {
//                PacketContainer packet = event.getPacket();
//                SpeedADetection.detect(event,packet);
//            }
//        });
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        SpeedADetection.detect(event.getPlayer());
        AirMoveADetection.detect(event.getPlayer());
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event){
//        Entity attacker = event.getDamager();
//        Entity victim = event.getEntity();

    }



}
