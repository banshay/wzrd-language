package com.banshay.language;

import com.banshay.language.types.WzrdNull;
import com.oracle.truffle.api.dsl.ImplicitCast;
import com.oracle.truffle.api.dsl.TypeCast;
import com.oracle.truffle.api.dsl.TypeCheck;
import com.oracle.truffle.api.dsl.TypeSystem;

@TypeSystem({int.class, double.class, long.class, boolean.class})
public class WzrdTypeSystem {

  @TypeCheck(WzrdNull.class)
  public static boolean isNull(Object value) {
    return value == WzrdNull.INSTANCE;
  }

  @TypeCast(WzrdNull.class)
  public static WzrdNull asWzrdNull(Object value) {
    assert isNull(value);
    return WzrdNull.INSTANCE;
  }

  @ImplicitCast
  public static double castInt(int value) {
    return value;
  }
}
