package mindurka;

import arc.util.*;
import arc.*;

import mindustry.core.*;
import mindustry.game.*;
import mindustry.mod.*;
import mindustry.gen.*;
import mindustry.Vars;

public class Template extends Plugin {

    @Override
    public void init() {

        Events.on(EventType.PlayerChatEvent.class, event -> {
            if (!event.message.startsWith("/")) {
                String prefix = event.player.admin() ? "[scarlet]Админ[white] | " : "[cyan]Игрок[white] | ";
                String playerName = NetClient.colorizeName(event.player.id, event.player.name);
                String uuid = event.player.id;
                if (uuid.equals("GYmJmGDY2McAAAAAN8z4Bg==")) {
                    prefix = "[sky]Owner[white] | ";
                Call.sendMessage(prefix + playerName + " [gold]>[white] " + event.message);
                Log.info(event.player.name + " > " + event.message);
            }
        });

        Vars.netServer.admins.addChatFilter((player, text) -> null);
    }

    //register commands that player can invoke in-game
    @Override
    public void registerClientCommands(CommandHandler handler){
        handler.removeCommand("a");
        handler.removeCommand("t");
        handler.<Player>register("a", "<текст...>", "Отправить сообщение от имени админа", (args, player) -> {
            if (!player.admin()) {
                player.sendMessage("[scarlet]Ты не админ!");
                return;
            }

            String message = args[0];
            String playerName = NetClient.colorizeName(player.id, player.name);

            Groups.player.each(Player::admin, otherPlayer -> {
                otherPlayer.sendMessage("<[scarlet]A[]>" + playerName + " [gold]>[#ff4449] " + message);
            });
        });
        handler.<Player>register("t", "<текст...>", "Отправить командное сообщение", (args, player) -> {
            String message = args[0];
            String playerName = NetClient.colorizeName(player.id, player.name);

            Groups.player.each(o -> o.team() == player.team(), otherPlayer -> {
                otherPlayer.sendMessage("<[#" + player.team().color + "]T[]>" + playerName + " [gold]>[lime] " + message);
            });
        });
    }
}
