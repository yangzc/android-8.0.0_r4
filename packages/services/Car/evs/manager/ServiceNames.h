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


// This is the name as which we'll register ourselves
const static char kManagedEnumeratorName[] = "EvsSharedEnumerator";

// This is the name of the hardware provider to which we'll bind
// TODO:  How should we configure these values to target appropriate hardware?
const static char kHardwareEnumeratorName[]  = "EvsEnumeratorHw-Mock";

