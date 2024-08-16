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


package com.openeggbert.core.sound;

import com.openeggbert.core.release.Release;
import lombok.Getter;

/**
 *
 * @author robertvokac
 */
public enum SoundType {
    CLICK(0),
    MEDIUM_JUMP(1),
    HIGH_JUMP(2),
    FALLING_ON_FLOOR(3),
    HEAD_WAS_BANGED_THE_HEAD_SOUND(4),
    TURNING_ON_THE_OTHER_SIDE(5),
    ALMOST_FALLED(6),
    LOOKING_DOWN(7),
    INSTANT_DEATH(8),
    NEW_LIFE(9),
    EXPLOSION(10),
    ONE_TREASURE_WAS_COLLECTED(11),
    ONE_EGG_WAS_COLLECTED(12),
    REACHED_GOAL_BUT_NOT_TREASURES_ARE_COLLECTED(13),
    REACHED_GOAL_BUT_AND_ALL_TREASURES_ARE_COLLECTED(14),
    HELICOPTER_IS_BEING_STARTED(15),
    HELICOPTER_IS_WORKING(16),
    HELICOPTER_IS_BEING_STOPPED(17),
    HELICOPTER_IS_WORKING_IN_REDUCED_POWER(18),
    //19
    //20
    //21
    LOG_JUMP(22),
    FALLING_IN_WATER(23),
    SWIMMING_IN_WATER(24),
    SAILING_TO_WATER_SURFACE(25),
    DROWNED_IN_THE_WATER(26),
    //27
    JEEP_IS_BEING_STARTED(28),
    JEEP_IS_WORKING(29),
    JEEP_IS_BEING_STOPPED(30),
    JEEP_IS_WORKING_IN_REDUCED_POWER(31),
    GOODBYE_BEFORE_VISITING_ANOTHER_WORLD(32),
    OPENING_DOORS(33),
    //34
    //35
    MONKEY_SOUND_IF_TOO_LONG_HANGING_ON_BAR_WITHOUT_AN_ACTIVITY(36),
    FEET_TAPPING(37),
    PUSHING_CASE_FORWARD(38),
    PULLING_CASE_BACKWARD(39),
    HEAD_WAS_BANGED_THE_BLUPI_VOICE(40),
    JUMPING_FROM_SPRING(41),
    COLLECTED_SHIELD_AND_INSTANTLY_USING(42),
    SHIELD_LOST_EFFECT(43),
    LOLLIPOP_EFFECT_STARTED(44),
    LOLLIPOP_EFFECT_ENDED(45),
    SIGHING_FROM_THE_FATIGUE_OF_PUSHING_THE_CASE(46),
    //47
    ANGRY_FROM_PULLING_THE_CASE_FOR_TOO_LONG_TIME(48),
    FEAR(49),
    LICKING_LOLLIPOP(50),
    //51
    //52
    EMPTY_TANK(53),
    //54
    RECHARGING_DEVICE_EFFECT_STARTING(55),
    RECHARGING_DEVICE_EFFECT_ENDING(56),
    DRINKING_INVISIBILITY_POTION(57),
    //58
    //59
    HAPPY_BECAUSE_COLLECTED_ONE_DYNAMITE(60),
    HAPPY_BECAUSE_FIRED_ONE_DYNAMITE(61),
    //62
    //63
    SPLASH_OF_WATER(64),
    STICKING_OUT_THE_TONGUE(65),
    //66-69
    CRUSHER_EFFECT(70),
    //71
    BRIDGE_IS_COLLAPSING(72);
    

    

//LAST_TREASURE_WAS_COLLECTED(12)

    ;
    @Getter
    private int number;
    SoundType(int numberIn, Release... featureLevels) {
        this.number = numberIn;
    }
    
}
