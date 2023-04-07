package ca.encodeous.pong.network.packets;

import java.nio.ByteBuffer;
import java.util.UUID;

public class ScoreUpdatePacket extends SerializablePacket {
    public int[] getScores() {
        return scores;
    }

    public void setScores(int[] scores) {
        this.scores = scores;
    }

    private int[] scores;

    @Override
    public PacketType getType() {
        return PacketType.SCORE_UPDATE;
    }

    @Override
    public void deserialize(ByteBuffer buf) {
        int len = buf.getInt();
        scores = new int[len];
        for(int i = 0; i < len; i++){
            scores[i] = buf.getInt();
        }
    }

    @Override
    public void serialize(ByteBuffer buf) {
        buf.putInt(scores.length);
        for (int score : scores) {
            buf.putInt(score);
        }
    }
}
