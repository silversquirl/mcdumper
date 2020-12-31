package vktec.packetextractor;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Formatter;
import net.minecraft.network.NetworkState;
import net.minecraft.network.NetworkSide;
import net.minecraft.network.Packet;

public class Main {
	public static void main(String[] args) throws IOException {
		FileWriter file = new FileWriter("packets.txt");
		Formatter f = new Formatter(file);

		for (NetworkState state : NetworkState.class.getEnumConstants()) {
			for (NetworkSide side : NetworkSide.class.getEnumConstants()) {
				if (state == NetworkState.HANDSHAKING && side == NetworkSide.CLIENTBOUND) continue;
				printPackets(f, state, side);
			}
		}

		file.close();
	}

	private static void printPackets(Formatter f, NetworkState state, NetworkSide side) {
		for (int i = 0;; i++) {
			Packet p;
			try {
				p = state.getPacketHandler(side, i);
			} catch (IndexOutOfBoundsException e) {
				break;
			}
			f.format("%s %d\n", p.getClass().getName().replaceFirst("^net\\.minecraft\\.network\\.packet\\.", ""), i);
		}
	}
}
