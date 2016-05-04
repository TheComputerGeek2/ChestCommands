package com.gmail.filoghost.chestcommands.internal;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.gmail.filoghost.chestcommands.bridge.EconomyBridge;
import com.gmail.filoghost.chestcommands.bridge.PlayerPointsBridge;

public class Variable {

	private static HashMap<String, Variable> vars;

	static {
		vars = new HashMap<String, Variable>();
		vars.put("{player}", new Variable("{player}") {
			public String getReplacement(Player executor) {
				return executor.getName();
			}
		});

		vars.put("{online}", new Variable("{online}") {
			public String getReplacement(Player executor) {
				return String.valueOf(CachedGetters.getOnlinePlayers());
			}
		});

		vars.put("{max_players}", new Variable("{max_players}") {
			public String getReplacement(Player executor) {
				return String.valueOf(Bukkit.getMaxPlayers());
			}
		});

		vars.put("{money}", new Variable("{money}") {
			public String getReplacement(Player executor) {
				if (EconomyBridge.hasValidEconomy()) {
					return EconomyBridge.formatMoney(EconomyBridge
							.getMoney(executor));
				} else {
					return "[ECONOMY PLUGIN NOT FOUND]";
				}
			}
		});

		vars.put("{points}", new Variable("{points}") {
			public String getReplacement(Player executor) {
				if (PlayerPointsBridge.hasValidPlugin()) {
					return String.valueOf(PlayerPointsBridge
							.getPoints(executor));
				} else {
					return "[PLAYER POINTS PLUGIN NOT FOUND]";
				}
			}
		});

		vars.put("{world}", new Variable("{world}") {
			public String getReplacement(Player executor) {
				return executor.getWorld().getName();
			}
		});
	}

	private String text;
	
	public Variable(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
	
	public static Map<String, Variable> getNameMap() {
		return Collections.unmodifiableMap(vars);
	}
	
	public String getReplacement(Player executor) {
		return null;
	}
	
	public static Collection<Variable> values() {
		return Collections.unmodifiableCollection(vars.values());
	}
	
	public static void addVariable(Variable var) {
		vars.put(var.text, var);
	}
}
