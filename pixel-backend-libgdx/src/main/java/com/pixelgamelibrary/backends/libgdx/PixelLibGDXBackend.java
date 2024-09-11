///////////////////////////////////////////////////////////////////////////////////////////////
// Pixel: Game library.
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
package com.pixelgamelibrary.backends.libgdx;

import com.badlogic.gdx.Application;
import static com.badlogic.gdx.Application.ApplicationType.Desktop;
import com.badlogic.gdx.Gdx;
import com.pixelgamelibrary.PixelException;
import com.pixelgamelibrary.Platform;
import com.pixelgamelibrary.api.AppI;
import com.pixelgamelibrary.api.AssetI;
import com.pixelgamelibrary.api.AudioI;
import com.pixelgamelibrary.api.GraphicsI;
import com.pixelgamelibrary.api.InputI;
import com.pixelgamelibrary.api.UtilsI;
import com.pixelgamelibrary.api.StorageI;
import com.pixelgamelibrary.api.NetI;
import com.pixelgamelibrary.api.PixelBackend;

/**
 *
 * @author robertvokac
 */
public class PixelLibGDXBackend implements PixelBackend {

    private AppI pixelAppLibGdxImpl = null;
    private GraphicsI pixelGraphicsLibGdxImpl = null;
    private AudioI pixelAudioLibGdxImpl = null;
    private InputI pixelInputLibGdxImpl = null;
    private NetI pixelNetLibGdxImpl = null;
    private AssetI pixelAssetLibGdxImpl = null;
    private StorageI pixelStorageLibGdxImpl = null;
    private UtilsI pixelUtilsLibGdxImpl = null;


    @Override
    public GraphicsI graphics() {
        if (pixelGraphicsLibGdxImpl == null) {
            pixelGraphicsLibGdxImpl = new GraphicsLibGDXImpl();
        }
        return pixelGraphicsLibGdxImpl;
    }

    @Override
    public AudioI audio() {

        if (pixelAudioLibGdxImpl == null) {
            pixelAudioLibGdxImpl = new AudioLibGDXImpl();
        }
        return pixelAudioLibGdxImpl;
    }

    @Override
    public InputI input() {

        if (pixelInputLibGdxImpl == null) {
            pixelInputLibGdxImpl = new InputLibGDXImpl();
        }
        return pixelInputLibGdxImpl;
    }

    @Override
    public NetI net() {

        if (pixelNetLibGdxImpl == null) {
            pixelNetLibGdxImpl = new NetLibGDXImpl();
        }
        return pixelNetLibGdxImpl;
    }

    @Override
    public AssetI asset() {

        if (pixelAssetLibGdxImpl == null) {
            pixelAssetLibGdxImpl = new AssetLibGDXImpl();
        }
        return pixelAssetLibGdxImpl;
    }

    @Override
    public StorageI storage() {

        if (pixelStorageLibGdxImpl == null) {
            pixelStorageLibGdxImpl = new StorageLibGDXImpl();
        }
        return pixelStorageLibGdxImpl;
    }

    @Override
    public UtilsI utils() {

        if (pixelUtilsLibGdxImpl == null) {
            pixelUtilsLibGdxImpl = new UtilsLibGDXImpl();
        }
        return pixelUtilsLibGdxImpl;    }

    @Override
    public AppI app() {
        if (pixelAppLibGdxImpl == null) {
            pixelAppLibGdxImpl = new AppLibGDXImpl();
        }
        return pixelAppLibGdxImpl;       }

}
