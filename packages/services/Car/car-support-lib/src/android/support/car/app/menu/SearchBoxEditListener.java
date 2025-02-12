/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package android.support.car.app.menu;

/**
 * A listener that listens the user input to the search box.
 * @hide
 */
public abstract class SearchBoxEditListener {
    /**
     * The user hit enter on the keyboard.
     */
    public abstract void onSearch(String text);

    /**
     * The user changed the text in the search box with the keyboard.
     */
    public abstract void onEdit(String text);
}