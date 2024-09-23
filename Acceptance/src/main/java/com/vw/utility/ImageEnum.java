package com.vw.utility;

import java.util.Arrays;

public enum ImageEnum {
	Volkswagen("Volks.png"),
	Skoda("skoda.png"),
	Audi("Audi.jpg"),
	VW_SouthAfrica("SouthAfrica.png"),
	SAVWIPL("Savwipl.jpg"),
	VW_Chattanoga("Chattanooga.png");
	

	private final String imgName;

	ImageEnum(String imgName) {
		this.imgName = imgName;
	}

	public String getImgName() {
		return imgName;
	}

	public static String getImageByBrandName(String brandName) {
		return Arrays.stream(ImageEnum.values())
				.filter(brand -> brandName.equalsIgnoreCase(brand.name()))
				.findFirst()
				.map(ImageEnum::getImgName)
				.orElse(null);
	}
}