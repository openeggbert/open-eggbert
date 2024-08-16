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
package com.openeggbert.core.fbox.impl.libgdx;

import com.badlogic.gdx.Application;
import static com.badlogic.gdx.Application.ApplicationType.Desktop;
import com.badlogic.gdx.Gdx;
import com.openeggbert.core.fbox.api.FBoxAssetInterface;
import com.openeggbert.core.fbox.api.FBoxAudioInterface;
import com.openeggbert.core.fbox.core.DefinitiveFrameworkException;
import com.openeggbert.core.fbox.entity.Platform;
import com.openeggbert.core.fbox.api.FBoxGraphicsInterface;
import com.openeggbert.core.fbox.api.FBoxInputInterface;
import com.openeggbert.core.fbox.api.FBoxInterface;
import com.openeggbert.core.fbox.api.FBoxNetInterface;
import com.openeggbert.core.fbox.api.FBoxStorageInterface;
import com.openeggbert.core.fbox.api.FBoxUtilsInterface;

/**
 *
 * @author robertvokac
 */
public class FBoxLibGDXImpl implements FBoxInterface {

    private FBoxGraphicsInterface fBoxGraphicsLibGdxImpl = null;
    private FBoxAudioInterface fBoxAudioLibGdxImpl = null;
    private FBoxInputInterface fBoxInputLibGdxImpl = null;
    private FBoxNetInterface fBoxNetLibGdxImpl = null;
    private FBoxAssetInterface fBoxAssetLibGdxImpl = null;
    private FBoxStorageInterface fBoxStorageLibGdxImpl = null;
    private FBoxUtilsInterface fBoxUtilsLibGdxImpl = null;

    @Override
    public void exit() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Platform getPlatform() {
        Application.ApplicationType applicationType = Gdx.app.getType();
        switch (applicationType) {
            case Desktop:
                return Platform.DESKTOP;
            case Android:
                return Platform.ANDROID;
            case WebGL:
                return Platform.WEB;
            default:
                throw new DefinitiveFrameworkException("Unsupported platform: " + applicationType);
        }
    }

    @Override
    public FBoxGraphicsInterface graphics() {
        if (fBoxGraphicsLibGdxImpl == null) {
            fBoxGraphicsLibGdxImpl = new FBoxGraphicsLibGDXImpl();
        }
        return fBoxGraphicsLibGdxImpl;
    }

    @Override
    public FBoxAudioInterface audio() {

        if (fBoxAudioLibGdxImpl == null) {
            fBoxAudioLibGdxImpl = new FBoxAudioLibGDXImpl();
        }
        return fBoxAudioLibGdxImpl;
    }

    @Override
    public FBoxInputInterface input() {

        if (fBoxInputLibGdxImpl == null) {
            fBoxInputLibGdxImpl = new FBoxInputLibGDXImpl();
        }
        return fBoxInputLibGdxImpl;
    }

    @Override
    public FBoxNetInterface net() {

        if (fBoxNetLibGdxImpl == null) {
            fBoxNetLibGdxImpl = new FBoxNetLibGDXImpl();
        }
        return fBoxNetLibGdxImpl;
    }

    @Override
    public FBoxAssetInterface asset() {

        if (fBoxAssetLibGdxImpl == null) {
            fBoxAssetLibGdxImpl = new FBoxAssetLibGDXImpl();
        }
        return fBoxAssetLibGdxImpl;
    }

    @Override
    public FBoxStorageInterface storage() {

        if (fBoxStorageLibGdxImpl == null) {
            fBoxStorageLibGdxImpl = new FBoxStorageLibGDXImpl();
        }
        return fBoxStorageLibGdxImpl;
    }

    @Override
    public FBoxUtilsInterface utils() {

        if (fBoxUtilsLibGdxImpl == null) {
            fBoxUtilsLibGdxImpl = new FBoxUtilsLibGDXImpl();
        }
        return fBoxUtilsLibGdxImpl;    }

}
