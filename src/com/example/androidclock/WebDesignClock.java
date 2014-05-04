package com.example.androidclock;

import java.util.ArrayList;
import java.util.List;

public class WebDesignClock {
	
		// current color & font
		public Integer mCurrentColor = -1;
		public Integer mCurrentFont  = -1;
		
		// background and fonts lists
		private List<BgColor> mBgcolors = new ArrayList<BgColor>();
		private List<Font> mFonts 		= new ArrayList<Font>();
		
		// Current settings 
		public List<Object> currentSettings = new ArrayList <Object>();

		/*
		 *  construtor
		 *  Feeds the lists
		 */ 
		public WebDesignClock(){
			
			// default bg
			currentSettings.add(new BgColor("black"		,"#000000"));
		
			// colors from http://flatuicolors.com/
			mBgcolors.add(new BgColor("turquoise"		,"#1ccca9"));
			mBgcolors.add(new BgColor("Sun Flower"		,"#f1c40f"));
			mBgcolors.add(new BgColor("Emerald"			,"#2ecc71"));
			mBgcolors.add(new BgColor("Peter River"		,"#3498db"));
			mBgcolors.add(new BgColor("Pomegranate"		,"#c0392b"));
			mBgcolors.add(new BgColor("Belize Hole"		,"#2980b9"));
			mBgcolors.add(new BgColor("Amethyst"		,"#9b59b6"));
			mBgcolors.add(new BgColor("Green Sea"		,"#16a085"));
			mBgcolors.add(new BgColor("Wisteria"		,"#8e44ad"));
			mBgcolors.add(new BgColor("Wet Asphalt"		,"#34495e"));
			mBgcolors.add(new BgColor("Midnight Blue"	,"#2c3e50"));
			mBgcolors.add(new BgColor("Orange"			,"#f39c12"));
			mBgcolors.add(new BgColor("Carrot"			,"#e67e22"));
			mBgcolors.add(new BgColor("Pumpkin"			,"#d35400"));
			mBgcolors.add(new BgColor("Nephritis"		,"#27ae60"));
			mBgcolors.add(new BgColor("Alizarin"		,"#e74c3c"));
			
			
			// fonts from http://www.fontsquirrel.com/fonts/<font name>
			mFonts.add(new Font("Lato"					,"fonts/Lato-Bol.ttf"));
			mFonts.add(new Font("Open Sans"				,"fonts/OpenSans-Bold.ttf"));
			mFonts.add(new Font("Aller"					,"fonts/Aller_Bd.ttf"));
			// http://dannci.deviantart.com/art/Gabo-Free-Elegant-Font-157988169
			mFonts.add(new Font("Gabo"					,"fonts/Gabo.otf"));
			mFonts.add(new Font("Railway"				,"fonts/Railway.otf"));
			mFonts.add(new Font("Ambrosia"				,"fonts/Ambrosia.ttf"));
			mFonts.add(new Font("Sertig"				,"fonts/Sertig.otf"));
			mFonts.add(new Font("Mido"					,"fonts/Mido.otf"));
			mFonts.add(new Font("Franchise"				,"fonts/Franchise-Bold.ttf"));

		}
		
		
		/* 
		 * returns the new color and font
		 */
		 public List<Object> UpdateLayout(Boolean updateBgColor, Boolean updateFont){
			 
			 if(updateBgColor){
				 mCurrentColor = mCurrentColor<mBgcolors.size()-1 ? mCurrentColor+1 : 0;
			 }
			 if(updateFont){
				 mCurrentFont  = mCurrentFont<mFonts.size()-1 ? mCurrentFont+1 : 0;
			 }

			 List<Object> infos = new ArrayList <Object>();
			 infos.add(mBgcolors.get(mCurrentColor));
			 infos.add(mFonts.get(mCurrentFont));
			 
			 return infos;
			 
		 }
		 
		 /*
		  *  reset font and color to initial state 
		  */
		 public void resetBgText(){
			 mCurrentColor = -1;
			 mCurrentFont  = -1;
		 }

}
