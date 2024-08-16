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
package com.openeggbert.entity.common;

import com.openeggbert.compatibility.Release;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;

/**
 *
 * @author robertvokac
 */
public enum ConfigDefKey {
    FULLSCREEN("FullScreen", new Release[]{Release.SPEEDY_BLUPI_DEMO}),
    STRICT_COMPATIBILITY("StrictCompatibility", new Release[]{Release.OPEN_EGGBERT_3});
    
    @Getter
    private String key;
    @Getter
    private final List<Release> featureLevels;
    ConfigDefKey(String keyIn, Release[] featureLevelsIn) {
        this.key = keyIn;
        List<Release> list = Arrays.asList(featureLevelsIn);
        Stream<Release> stream = list.stream();
        this.featureLevels = stream.collect(Collectors.toList());
    }
    
}
