package org.itsrune.scoobydoo.Checks.Movement;

import com.google.common.collect.Sets;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.itsrune.scoobydoo.Checks.Check;
import org.itsrune.scoobydoo.Misc;
import org.itsrune.scoobydoo.Scoobydoo;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class SpeedA extends Check {
    public SpeedA() {
        super(Misc.CheckType.MOVEMENT, true);
    }


    public Set<UUID> prevPlayersOnGround = Sets.newHashSet();

    public static HashMap<UUID, Long> playerJumpDelay = new HashMap<>();

    public void detect(Player player){
        Block block = player.getLocation().add(0, 2, 0).getBlock();

        if (player.getLocation().add(1, 2, 0).getBlock().getType() != Material.AIR){ return; }
        if (player.getLocation().add(1, 2, 1).getBlock().getType() != Material.AIR){ return; }

        if (!(playerJumpDelay.containsKey(player.getUniqueId()))) { playerJumpDelay.put(player.getUniqueId(), 0L); }

        if (player.getVelocity().getY() > 0) {
            double jumpVelocity = 0.42F;
            if (player.getLocation().getBlock().getType() != Material.LADDER && prevPlayersOnGround.contains(player.getUniqueId())) {
                if (!player.isOnGround() && Double.compare(player.getVelocity().getY(), jumpVelocity) == 0) { // Resort to alternative as isOnGround method can be altered by client.
                    Bukkit.broadcastMessage(String.valueOf(System.currentTimeMillis() - playerJumpDelay.get(player.getUniqueId())));

                    long diff = System.currentTimeMillis() - playerJumpDelay.get(player.getUniqueId());
                    if (diff < 449 && block.getType() == Material.AIR){
                        Bukkit.broadcastMessage(String.valueOf(block.getType()));
                        Scoobydoo.FlagPlayer(player, "SpeedA", "Player jumped too fast " + Scoobydoo.PLUGIN_COLOR + "Expected§7: §f499-600§7, " + Scoobydoo.PLUGIN_COLOR  + "Recieved§7: §f" + diff);
                    }
                    playerJumpDelay.put(player.getUniqueId(), System.currentTimeMillis());
                }
            }
        }

        if (player.isOnGround()) { // Resort to alternative as isOnGround method can be altered by client.
            prevPlayersOnGround.add(player.getUniqueId());
        } else {
            prevPlayersOnGround.remove(player.getUniqueId());
        }
    }

}
