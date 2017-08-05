package com.godaddy.icfp2017.models.serialization;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.godaddy.icfp2017.models.State;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class BinaryStateSerializer extends StdSerializer<State> {
  public BinaryStateSerializer() {
    super(State.class);
  }

  @Override
  public void serialize(
      final State value,
      final JsonGenerator gen,
      final SerializerProvider provider) throws IOException {

    final Kryo kryo = KryoFactory.createKryo();

    final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

    try (final Output output = new Output(byteArrayOutputStream)) {
      kryo.writeObject(output, value);
    }

    final byte[] data = byteArrayOutputStream.toByteArray();
    gen.writeBinary(data);
  }
}

