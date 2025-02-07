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

#define LOG_TAG "EvsManager"

#include <unistd.h>

#include <hidl/HidlTransportSupport.h>
#include <utils/Errors.h>
#include <utils/StrongPointer.h>
#include <utils/Log.h>

#include "ServiceNames.h"
#include "Enumerator.h"


// libhidl:
using android::hardware::configureRpcThreadpool;
using android::hardware::joinRpcThreadpool;

// Generated HIDL files
using android::hardware::automotive::evs::V1_0::IEvsEnumerator;
using android::hardware::automotive::evs::V1_0::IEvsDisplay;

// The namespace in which all our implementation code lives
using namespace android::automotive::evs::V1_0::implementation;
using namespace android;


int main() {
    // Prepare the RPC serving thread pool.  We're configuring it with no additional
    // threads beyond the main thread which will "join" the pool below.
    configureRpcThreadpool(1, true /* callerWillJoin */);

    ALOGI("EVS managed service connecting to hardware at %s", kHardwareEnumeratorName);
    android::sp<Enumerator> service = new Enumerator();
    if (!service->init(kHardwareEnumeratorName)) {
        ALOGE("Failed to initialize");
        return 1;
    }

    // Register our service -- if somebody is already registered by our name,
    // they will be killed (their thread pool will throw an exception).
    ALOGI("EVS managed service is starting as %s", kManagedEnumeratorName);
    status_t status = service->registerAsService(kManagedEnumeratorName);
    if (status == OK) {
        ALOGD("Registration complete");

        // Send this main thread to become a permanent part of the thread pool.
        // This is not expected to return.
        joinRpcThreadpool();
    } else {
        ALOGE("Could not register service %s (%d).", kManagedEnumeratorName, status);
    }

    // In normal operation, we don't expect the thread pool to exit
    ALOGE("EVS Hardware Enumerator is shutting down");
    return 1;
}
