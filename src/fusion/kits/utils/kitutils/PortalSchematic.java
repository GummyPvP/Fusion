package fusion.kits.utils.kitutils;

import java.io.File;
import java.io.FileInputStream;

import org.bukkit.Location;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;

import fusion.main.Fusion;

public class PortalSchematic {
	
	private Clipboard clipboard = null;
	
	private int ID = 0;
	
	public PortalSchematic(int id) {
		ID = id;
		loadSchematic();
	}
	
	private void loadSchematic() {
		File schemFile = new File(Fusion.getInstance().getDataFolder(), "schematics/pocket" + ID + ".schem");
		
		ClipboardFormat format = ClipboardFormats.findByFile(schemFile);
		
		try (ClipboardReader reader = format.getReader(new FileInputStream(schemFile))) {
			clipboard = reader.read();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void pasteSchematic(Location location) {
		System.out.println(ID);
		if (clipboard != null) {
			try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(new BukkitWorld(location.getWorld()), -1)) {
			    Operation operation = new ClipboardHolder(clipboard)
			            .createPaste(editSession)
			            .to(BlockVector3.at(location.getX(), location.getY(), location.getZ()))
			            .ignoreAirBlocks(false)
			            .build();
			    Operations.complete(operation);
			} catch (WorldEditException e) {
				e.printStackTrace();
			}
		}
	}

}
