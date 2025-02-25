/*
 * Copyright (c) 2025 Arthroverse Laboratory
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Organization: Arthroverse Laboratory
 * Author: Vinh Dinh Mai
 * Contact: business@arthroverse.com
 *
 *
 * @author ducksabervn
 */
package com.ducksabervn.duckyemulator.Utilities.Constant;

public enum WarningMessage{

    UNIVERSAL_RESET_FIELD("Are you sure you want to reset all fields ?");

    private String message;
    private WarningMessage(String message){
        this.message = message;
    }

    @Override
    public String toString(){
        return this.message;
    }
}
