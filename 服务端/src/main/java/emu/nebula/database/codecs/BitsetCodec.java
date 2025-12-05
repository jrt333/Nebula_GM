package emu.nebula.database.codecs;

import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import emu.nebula.util.Bitset;

import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;

/**
 * Custom mongodb codec for encoding/decoding fastutil int sets.
 */
public class BitsetCodec implements Codec<Bitset> {
    
    @Override
    public Class<Bitset> getEncoderClass() {
        return Bitset.class;
    }

    @Override
    public void encode(BsonWriter writer, Bitset bitset, EncoderContext encoderContext) {
        writer.writeStartArray();
        for (long value : bitset.getData()) {
            writer.writeInt64(value);
        }
        writer.writeEndArray();
    }
    
    @Override
    public Bitset decode(BsonReader reader, DecoderContext decoderContext) {
        LongList array = new LongArrayList();
        
        reader.readStartArray();
        while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            array.add(reader.readInt64());
        }
        reader.readEndArray();
        
        return new Bitset(array.toLongArray());
    }
}