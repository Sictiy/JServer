// automatically generated by the FlatBuffers compiler, do not modify

package com.sictiy.jserver.entry.buffer;

import java.nio.*;
import java.lang.*;
import java.util.*;
import com.google.flatbuffers.*;

@SuppressWarnings("unused")
public final class RegisterRsp extends Table {
  public static RegisterRsp getRootAsRegisterRsp(ByteBuffer _bb) { return getRootAsRegisterRsp(_bb, new RegisterRsp()); }
  public static RegisterRsp getRootAsRegisterRsp(ByteBuffer _bb, RegisterRsp obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__assign(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public void __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; vtable_start = bb_pos - bb.getInt(bb_pos); vtable_size = bb.getShort(vtable_start); }
  public RegisterRsp __assign(int _i, ByteBuffer _bb) { __init(_i, _bb); return this; }

  public boolean result() { int o = __offset(4); return o != 0 ? 0!=bb.get(o + bb_pos) : false; }

  public static int createRegisterRsp(FlatBufferBuilder builder,
      boolean result) {
    builder.startObject(1);
    RegisterRsp.addResult(builder, result);
    return RegisterRsp.endRegisterRsp(builder);
  }

  public static void startRegisterRsp(FlatBufferBuilder builder) { builder.startObject(1); }
  public static void addResult(FlatBufferBuilder builder, boolean result) { builder.addBoolean(0, result, false); }
  public static int endRegisterRsp(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
}
