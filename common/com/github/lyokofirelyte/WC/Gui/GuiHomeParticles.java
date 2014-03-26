package com.github.lyokofirelyte.WC.Gui;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import com.github.lyokofirelyte.WC.WCMain;
import com.github.lyokofirelyte.WCAPI.WCGui;
import com.github.lyokofirelyte.WCAPI.WCUtils;

import static com.github.lyokofirelyte.WCAPI.Manager.InventoryManager.createItem;

public class GuiHomeParticles extends WCGui {
	
	private WCMain main;
	private WCGui parent;
	
	public GuiHomeParticles(WCMain main, WCGui parent){
		
		super(18, "&3HOME PARTICLES");
		this.main = main;
		this.parent = parent;
		
	}
	
	@Override
	public void create(){
		
		this.addButton(0, createItem("&aDefault", new String[] { "&e&o**Your default effects!**" }, Material.ENDER_PEARL));
		this.addButton(1, createItem("&aYou're like Katniss Everdeen", new String[] { "&e&o**Requires the rank Dweller!**" }, Material.FIRE));
		this.addButton(2, createItem("&aMuch bright, many blue, wow", new String[] { "&e&o**Requires the rank Settler!**" }, Material.BEACON));
		this.addButton(3, createItem("&aFor the miners", new String[] { "&e&o**Requires the rank Villager!**" }, Material.LAPIS_ORE));
		this.addButton(4, createItem("&aFor the eskimos", new String[] { "&e&o**Requires the rank Townsman!**" }, Material.PACKED_ICE));
		this.addButton(5, createItem("&aCool particles!", new String[] { "&e&o**Requires the rank Citizen!**" }, Material.WOOD_BUTTON));
		this.addButton(6, createItem("&aPortable particles", new String[] { "&e&o**Requires the rank Metro!**" }, Material.ENDER_CHEST));
		this.addButton(7, createItem("&aSpecial particle!", new String[] { "&e&o**Requires the rank Shirian!**" }, Material.ENDER_PORTAL));
		this.addButton(8, createItem("&aFor the horses under us", new String[] { "&e&o**Requires the rank Districtman!**" }, Material.HAY_BLOCK));
		this.addButton(13, createItem("&bWATERCLOSET CORE v5", new String[] { "&b< < <" }, Enchantment.DURABILITY, 10, Material.FLINT));
		
	}
	
	@Override
	public void actionPerformed(Player p){
		
		switch (this.slot){
		
		case 0:
			
			main.wcm.getWCPlayer(p.getName()).setHomeEffect("Default");
			WCUtils.s(p, "Home particle updated!");
			break;
		
		case 1: default:
				main.wcm.getWCPlayer(p.getName()).setHomeEffect(item.getType().name());
				WCUtils.s(p, "Home particle updated!");
			break;
			
		case 2:
			if(p.hasPermission("wa.dweller")){
				main.wcm.getWCPlayer(p.getName()).setHomeEffect(item.getType().name());
				WCUtils.s(p, "Home particle updated!");
			}
			break;
			
		case 3:
			if(p.hasPermission("wa.settler")){
				main.wcm.getWCPlayer(p.getName()).setHomeEffect(item.getType().name());
				WCUtils.s(p, "Home particle updated!");
			}
			break;

		case 4:
			if(p.hasPermission("wa.villager")){
				main.wcm.getWCPlayer(p.getName()).setHomeEffect(item.getType().name());
				WCUtils.s(p, "Home particle updated!");
			}
			break;

		case 5:
			if(p.hasPermission("wa.townsman")){
				main.wcm.getWCPlayer(p.getName()).setHomeEffect(item.getType().name());
				WCUtils.s(p, "Home particle updated!");
			}
			break;

		case 6:
			if(p.hasPermission("wa.citizen")){
				main.wcm.getWCPlayer(p.getName()).setHomeEffect(item.getType().name());
				WCUtils.s(p, "Home particle updated!");
			}
			break;

		case 7:
			if(p.hasPermission("wa.metro")){
				main.wcm.getWCPlayer(p.getName()).setHomeEffect(item.getType().name());
				WCUtils.s(p, "Home particle updated!");
			}
			break;

		case 8:
			if(p.hasPermission("wa.shirian")){
				main.wcm.getWCPlayer(p.getName()).setHomeEffect(item.getType().name());
				WCUtils.s(p, "Home particle updated!");
			}
			break;

		case 9:
			if(p.hasPermission("wa.districtman")){
				main.wcm.getWCPlayer(p.getName()).setHomeEffect(item.getType().name());
				WCUtils.s(p, "Home particle updated!");
			}
			break;
			
		case 13:
			
			this.main.wcm.displayGui(p, this.parent);
			break;
		}
	}
}