///////////////////////////////////////////////////////////////////////////////////////////////
// Open Eggbert: Free recreation of the computer game Speedy Eggbert.
// Copyright (C) 2024 the original author or authors.
//
// This program is free software: you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// as published by the Free Software Foundation, either version 3
// of the License, or (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program. If not, see 
// <https://www.gnu.org/licenses/> or write to the Free Software
// Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
///////////////////////////////////////////////////////////////////////////////////////////////
package com.openeggbert.lwjgl3.debugging;

import com.pixelgamelibrary.api.Pixel;
import com.pixelgamelibrary.backend.libgdx.interfaces.PixelBackendLibGDX;
import com.pixelgamelibrary.api.utils.AssetsTxt;
import java.util.List;
import java.util.Scanner;

/**
 *f
 * @author robertvokac
 */
public class DebuggingAssetsTxt {

    private DebuggingAssetsTxt() {
        //Not meant to be instantiated.
    }

    public static void main(String[] args) {
        Pixel.initBackend(new PixelBackendLibGDX());

        AssetsTxt a = new AssetsTxt(ASSETS_TXT);
//        a.listFiles();
//        System.out.println("-----");
//        a.listDirectories();
        System.out.println("Root has these");
        a.listFiles("embedded_mods/epsitec/speedy_blupi_I_legacy_full").forEach(l -> System.out.println("Root has this: " + l));

        System.out.println("\n\n\n\n");

        String nextFile = ".";
        while (true) {
            if (nextFile.equals("..")) {
                var array = nextFile.split("/");
                if (array.length == 1) {
                    nextFile = ".";
                } else {
                    nextFile = nextFile.substring(0, nextFile.length() - 1 - array[array.length - 1].length());
                }
            }
            nextFile = browse(nextFile, a);
            if (nextFile == null) {
                break;
            }
        }
    }

    private static String browse(String file, AssetsTxt a) {
        System.out.println("Calling browser(" + file + "," + a + ")");
        List<String> files = a.listFiles(file);
        List<String> directories = a.listDirectories(file);
        System.out.println("[" + 1 + "] D ..");
        System.out.println("[" + 2 + "] D .");
        int z = 3;
        for (int i = 0; i < directories.size(); i++, z++) {
            System.out.println("[" + z + "] D " + directories.get(i));
        }
        for (int i = 0; i < files.size(); i++, z++) {
            System.out.println("[" + (" ") + "] F " + files.get(i));
        }
        System.out.print("Option: ");
        Scanner s = new Scanner(System.in);

        String selected = s.next();
        if (selected.equals("exit")) {
            return null;
        }
        int selectedInt = -1;
        try {
            selectedInt = Integer.parseInt(selected);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            selectedInt = 2;
        }
        if (selectedInt == 1) {
            if (file.equals(".")) {
                //cannot go up a level
                return file;
            }
            var array = file.split("/");
            if (array.length == 1) {
                return "..";
            }
            var endIndex = file.length() - 1 - (array[array.length - 1].length());
            return file.substring(0, endIndex);
        }
        if (selectedInt == 2) {
            return file;
        }
        if (selectedInt < (directories.size() + 3)) {
            return (file.equals(".") ? "" : (file + "/")) + directories.get(-3 + selectedInt);
        }
//        if (selectedInt < (directories.size() + files.size() + 3)) {
//            return (file.equals(".") ? "" : (file + "/")) + files.get(-3 + directories.size() + selectedInt);
//        }
        return file;
    }

    private static final String ASSETS_TXT = "ui/uiskin.png\n" + "ui/uiskin.json\n" + "ui/font.fnt\n" + "ui/uiskin.atlas\n" + "ui/font-list.fnt\n" + "ui/font-subtitle.fnt\n" + "ui/font-window.fnt\n" + "default-spritesheets/speedy_blupi_I.spritesheet.csv.computed.csv\n" + "default-spritesheets/speedy_blupi_I.spritesheet.csv\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_music/MUSIC004.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_music/MUSIC000.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_music/MUSIC008.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_music/MUSIC001.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_music/MUSIC003.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_music/MUSIC009.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_music/MUSIC005.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_music/MUSIC007.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_music/MUSIC006.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_music/MUSIC002.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/WORLD044.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/WORLD108.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/WORLD205.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/WORLD057.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/WORLD202.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/WORLD063.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/DEMO202.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/WORLD020.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/DEMO205.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/WORLD206.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/WORLD065.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/WORLD025.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/WORLD042.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/WORLD031.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/WORLD110.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/DEMO204.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/WORLD070.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/WORLD056.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/DEMO206.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/DEMO200.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/WORLD073.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/WORLD062.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/WORLD045.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/WORLD001.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/WORLD055.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/WORLD054.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/WORLD066.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/WORLD021.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/WORLD043.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/WORLD032.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/WORLD071.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/WORLD052.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/WORLD041.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/WORLD203.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/WORLD099.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/Config.def\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/WORLD022.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/WORLD061.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/WORLD104.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/DEMO201.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/WORLD040.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/WORLD102.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/WORLD030.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/WORLD200.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/DEMO203.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/WORLD010.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/WORLD103.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/WORLD050.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/WORLD107.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/WORLD023.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/WORLD106.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/WORLD051.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/WORLD075.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/WORLD034.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/WORLD033.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/WORLD105.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/WORLD074.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/WORLD058.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/WORLD201.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/WORLD060.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/WORLD072.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/WORLD064.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/WORLD109.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/WORLD101.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/WORLD204.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/WORLD053.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_data/WORLD024.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image08/IMAGE08/INSERT.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image08/IMAGE08/DECOR008.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image08/IMAGE08/OBJECT.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image08/IMAGE08/DECOR023.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image08/IMAGE08/DECOR021.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image08/IMAGE08/LITTLE.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image08/IMAGE08/DECOR006.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image08/IMAGE08/SERVICE.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image08/IMAGE08/WIN.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image08/IMAGE08/SETUP.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image08/IMAGE08/NAME.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image08/IMAGE08/DECOR001.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image08/IMAGE08/CLEAR.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image08/IMAGE08/JAUGE.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image08/IMAGE08/DECOR011.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image08/IMAGE08/DECOR013.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image08/IMAGE08/REGION.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image08/IMAGE08/DECOR020.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image08/IMAGE08/BYE.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image08/IMAGE08/STOP.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image08/IMAGE08/DECOR014.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image08/IMAGE08/INFO.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image08/IMAGE08/MAP.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image08/IMAGE08/BLUPI000.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image08/IMAGE08/HELP.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image08/IMAGE08/BLUPI002.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image08/IMAGE08/READ.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image08/IMAGE08/TEMP.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image08/IMAGE08/DECOR002.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image08/IMAGE08/MOVIE.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image08/IMAGE08/ELEMENT.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image08/IMAGE08/MULTI.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image08/IMAGE08/TEXT.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image08/IMAGE08/BLUPI001.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image08/IMAGE08/LOST.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image08/IMAGE08/DECOR009.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image08/IMAGE08/DECOR000.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image08/IMAGE08/DECOR019.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image08/IMAGE08/DECOR012.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image08/IMAGE08/DECOR016.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image08/IMAGE08/DECOR004.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image08/IMAGE08/CREATE.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image08/IMAGE08/SESSION.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image08/IMAGE08/DECOR015.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image08/IMAGE08/BLUPI003.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image08/IMAGE08/BUTTON00.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image08/IMAGE08/DECOR022.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image08/IMAGE08/DECOR005.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image08/IMAGE08/INIT.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image08/IMAGE08/DECOR010.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image08/IMAGE08/WRITE.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image08/IMAGE08/MUSIC.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image08/IMAGE08/DECOR018.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image08/IMAGE08/DECOR007.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image08/IMAGE08/GREAD.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image08/IMAGE08/EXPLO.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image08/IMAGE08/GAMER.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image08/IMAGE08/DECOR017.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image08/IMAGE08/DECOR003.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image08/IMAGE08/GWRITE.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image08/mod.xml\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND001.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND048.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND003.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND050.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND056.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND025.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND053.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND029.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND040.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND007.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND063.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND033.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND044.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND042.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND005.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND010.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND000.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND013.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND022.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND016.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND062.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND035.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND032.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND012.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND037.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND020.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND058.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND031.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND049.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND004.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND038.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND018.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND064.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND047.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND046.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND008.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND030.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND041.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND019.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND065.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND051.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND039.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND021.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND014.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND011.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND060.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND006.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND023.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND028.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND015.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND027.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND052.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND045.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND017.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND036.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND002.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND054.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND055.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND009.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND024.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND059.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND026.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND043.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND061.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND034.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_sound/SOUND057.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_full/mod.xml\n" + "embedded_mods/epsitec/speedy_blupi_II_legacy_full/mod.xml_\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image16/INSERT.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image16/DECOR008.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image16/DECOR023.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image16/DECOR021.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image16/DECOR006.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image16/SERVICE.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image16/WIN.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image16/SETUP.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image16/NAME.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image16/DECOR001.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image16/CLEAR.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image16/DECOR011.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image16/DECOR013.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image16/REGION.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image16/DECOR020.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image16/BYE.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image16/STOP.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image16/DECOR014.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image16/INFO.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image16/HELP.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image16/READ.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image16/DECOR002.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image16/MULTI.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image16/LOST.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image16/DECOR000.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image16/DECOR019.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image16/DECOR012.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image16/DECOR016.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image16/DECOR004.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image16/CREATE.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image16/SESSION.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image16/DECOR015.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image16/DECOR022.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image16/DECOR005.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image16/INIT.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image16/DECOR010.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image16/WRITE.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image16/MUSIC.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image16/DECOR018.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image16/GREAD.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image16/GAMER.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image16/DECOR017.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image16/DECOR003.BLP\n" + "embedded_mods/epsitec/speedy_blupi_I_legacy_image16/GWRITE.BLP\n" + "embedded_mods/README.md\n" + "embedded_mods/open-eggbert/open_eggbert_free_image24x2/.gitkeep\n" + "embedded_mods/open-eggbert/open_eggbert_free_sound/.gitkeep\n" + "embedded_mods/open-eggbert/open_eggbert_free_music/.gitkeep\n" + "embedded_mods/open-eggbert/open_eggbert_free_image24/.gitkeep\n" + "com/badlogic/gdx/utils/lsans-15.fnt\n" + "com/badlogic/gdx/utils/lsans-15.png\n" + "libgdx.png\n";
}
