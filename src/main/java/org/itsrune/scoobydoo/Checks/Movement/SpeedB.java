package org.itsrune.scoobydoo.Checks.Movement;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.itsrune.scoobydoo.Checks.Check;
import org.itsrune.scoobydoo.Misc;
import org.itsrune.scoobydoo.Scoobydoo;

import java.util.HashMap;
import java.util.UUID;

public class SpeedB extends Check {

    public static HashMap<UUID, Double> playerVelocityMovement = new HashMap<>();
    public SpeedB() {
        super(Misc.CheckType.MOVEMENT, true);
    }

    public static boolean inWebs(Player p){
        return (p.getLocation().getBlock() != null && p.getLocation().getBlock().getType() == Material.WEB)
                || (p.getLocation().getBlock().getRelative(BlockFace.UP).getType() != null && p.getLocation().getBlock().getRelative(BlockFace.UP).getType() == Material.WEB);
    }

    public void detect(Player player){
        if (inWebs(player)) { return; }
        if (!(playerVelocityMovement.containsKey(player.getUniqueId()))) { playerVelocityMovement.put(player.getUniqueId(), 0D); }

        if (!(player.isOnGround())){ // Resort to alternative as isOnGround method can be altered by client.
            double valueCalculated = Math.abs(player.getVelocity().getZ() + player.getVelocity().getY() + player.getVelocity().getZ());
            Block block = player.getLocation().subtract(0, 1, 0).getBlock();
            if (valueCalculated == playerVelocityMovement.get(player.getUniqueId()) && !block.getType().equals(Material.SLIME_BLOCK)){
                if (!(player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR))){
                    Scoobydoo.FlagPlayer(player, "SpeedB", "Player has continuous Velocity throughout movement.");
                }

            }
            playerVelocityMovement.put(player.getUniqueId(), valueCalculated);
        }
    }
}
