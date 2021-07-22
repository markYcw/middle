package com.kedacom.middleware.mt.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * 终端类型
 * @author TaoPeng
 *
 */
public enum MTType {

	/**
	 * 桌面终端
	 */
	未知类型(0),
	MT_PC(1),
	MT_8010(2),
	MT_1010A(3),
	MT_8010Aplus(4),
	MT_8010C(5),
	MT_8010C1(6),
	MT_IMT(7),
	MT_TS6610(8),
	MT_8220B(9),
	MT_8220C(10),
	MT_8620A(11),
	MT_TS6610E(12),
	MT_TS6210(13),
	MT_8010A_2(14),
	MT_8010A_4(15),
	MT_8010A_8(16),
	MT_7210(17),
	MT_7610(18),
	MT_TS5610(19),
	MT_8220A(20),
	MT_7810(21),
	MT_7910(22),
	MT_7620_A(23),
	MT_7620_B(24),
	MT_7820_A(25),
	MT_7820_B(26),
	MT_7920_A(27),
	MT_7920_B(28),
	MT_KDV1000(29),
	MT_7921_L(30),
	MT_7921_H(31),
	MT_H600_LB(32),
	MT_H600_B(33),
	MT_H600_C(34),
	MT_H700_A(35),
	MT_H700_B(36),
	MT_H700_C(37),
	MT_H900_A(38),
	MT_H900_B(39),
	MT_H900_C(40),
	MT_H600_LC(41),
	MT_H800_A(42),
	MT_H800_B(43),
	MT_H800_C(44),
	MT_H800_TP(45),
	MT_H850_A(46),
	MT_H850_B(47),
	MT_H850_C(48),
	MT_H600_L_TP(49),
	MT_H600_TP(50),
	MT_H700_TP(51),
	MT_H850_TP(52),
	MT_H900_TP(53),
	MT_H650_LB(56),
	MT_SKY_310(85),
	MT_D510_TP(88)

	;

	private int value; 
	private MTType(int value){
		this.value = value;
	};
	
	public int getValue(){
		return this.value;
	}
	
	private static Map<Integer, MTType> maps = null;

	private static void initMap() {
		MTType[] values = MTType.values();
		maps = new HashMap<Integer, MTType>(values.length);
		for (MTType type : values) {
			maps.put(type.getValue(), type);
		}
	}
	
	public static MTType parse(int value){
		if(maps == null){
			initMap();
		}
		return maps.get(value);
	}
	
}
