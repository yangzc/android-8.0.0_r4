/*
 * Copyright (C) 2017 The Android Open Source Project
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
 * limitations under the License
 */

package android.inputmethodservice.cts.devicetest;

import static android.inputmethodservice.cts.DeviceEvent.isFrom;
import static android.inputmethodservice.cts.DeviceEvent.isNewerThan;
import static android.inputmethodservice.cts.DeviceEvent.isType;
import static android.inputmethodservice.cts.common.DeviceEventConstants.DeviceEventType.ON_CREATE;
import static android.inputmethodservice.cts.common.DeviceEventConstants.DeviceEventType.ON_DESTROY;
import static android.inputmethodservice.cts.common.DeviceEventConstants.DeviceEventType.ON_START_INPUT;
import static android.inputmethodservice.cts.common.ImeCommandConstants.ACTION_IME_COMMAND;
import static android.inputmethodservice.cts.common.ImeCommandConstants.COMMAND_SWITCH_INPUT_METHOD;
import static android.inputmethodservice.cts.common.ImeCommandConstants.EXTRA_ARG_STRING1;
import static android.inputmethodservice.cts.common.ImeCommandConstants.EXTRA_COMMAND;
import static android.inputmethodservice.cts.devicetest.BusyWaitUtils.pollingCheck;
import static android.inputmethodservice.cts.devicetest.MoreCollectors.startingFrom;

import android.inputmethodservice.cts.DeviceEvent;
import android.inputmethodservice.cts.common.DeviceEventConstants.DeviceEventType;
import android.inputmethodservice.cts.common.Ime1Constants;
import android.inputmethodservice.cts.common.Ime2Constants;
import android.inputmethodservice.cts.common.test.DeviceTestConstants;
import android.inputmethodservice.cts.common.test.ShellCommandUtils;
import android.inputmethodservice.cts.devicetest.SequenceMatcher.MatchResult;
import android.os.SystemClock;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Collector;

@RunWith(AndroidJUnit4.class)
public class InputMethodServiceDeviceTest {

    private static final long TIMEOUT = TimeUnit.SECONDS.toMillis(5);

    /** Test to check CtsInputMethod1 receives onCreate and onStartInput. */
    @Test
    public void testCreateIme1() throws Throwable {
        final TestHelper helper = new TestHelper(getClass(), DeviceTestConstants.TEST_CREATE_IME1);

        pollingCheck(() -> helper.queryAllEvents()
                        .collect(startingFrom(helper.isStartOfTest()))
                        .filter(isFrom(Ime1Constants.CLASS).and(isType(ON_CREATE)))
                        .findAny().isPresent(),
                TIMEOUT, "CtsInputMethod1.onCreate is called");

        final long startActivityTime = SystemClock.uptimeMillis();
        helper.launchActivity(DeviceTestConstants.PACKAGE, DeviceTestConstants.TEST_ACTIVITY_CLASS);

        pollingCheck(() -> helper.queryAllEvents()
                        .filter(isNewerThan(startActivityTime))
                        .filter(isFrom(Ime1Constants.CLASS).and(isType(ON_START_INPUT)))
                        .findAny().isPresent(),
                TIMEOUT, "CtsInputMethod1.onStartInput is called");
    }

    /** Test to check IME is switched from CtsInputMethod1 to CtsInputMethod2. */
    @Test
    public void testSwitchIme1ToIme2() throws Throwable {
        final TestHelper helper = new TestHelper(
                getClass(), DeviceTestConstants.TEST_SWITCH_IME1_TO_IME2);

        pollingCheck(() -> helper.queryAllEvents()
                        .collect(startingFrom(helper.isStartOfTest()))
                        .filter(isFrom(Ime1Constants.CLASS).and(isType(ON_CREATE)))
                        .findAny().isPresent(),
                TIMEOUT, "CtsInputMethod1.onCreate is called");

        final long startActivityTime = SystemClock.uptimeMillis();
        helper.launchActivity(DeviceTestConstants.PACKAGE, DeviceTestConstants.TEST_ACTIVITY_CLASS);

        pollingCheck(() -> helper.queryAllEvents()
                        .filter(isNewerThan(startActivityTime))
                        .filter(isFrom(Ime1Constants.CLASS).and(isType(ON_START_INPUT)))
                        .findAny().isPresent(),
                TIMEOUT, "CtsInputMethod1.onStartInput is called");

        helper.findUiObject(R.id.text_entry).click();

        // Switch IME from CtsInputMethod1 to CtsInputMethod2.
        final long switchImeTime = SystemClock.uptimeMillis();
        helper.shell(ShellCommandUtils.broadcastIntent(
                ACTION_IME_COMMAND, Ime1Constants.PACKAGE,
                "-e", EXTRA_COMMAND, COMMAND_SWITCH_INPUT_METHOD,
                "-e", EXTRA_ARG_STRING1, Ime2Constants.IME_ID));

        pollingCheck(() -> helper.shell(ShellCommandUtils.getCurrentIme())
                        .equals(Ime2Constants.IME_ID),
                TIMEOUT, "CtsInputMethod2 is current IME");
        pollingCheck(() -> helper.queryAllEvents()
                        .filter(isNewerThan(switchImeTime))
                        .filter(isFrom(Ime1Constants.CLASS).and(isType(ON_DESTROY)))
                        .findAny().isPresent(),
                TIMEOUT, "CtsInputMethod1.onDestroy is called");
        pollingCheck(() -> helper.queryAllEvents()
                        .filter(isNewerThan(switchImeTime))
                        .filter(isFrom(Ime2Constants.CLASS))
                        .collect(sequenceOfTypes(ON_CREATE, ON_START_INPUT))
                        .matched(),
                TIMEOUT,
                "CtsInputMethod2.onCreate and onStartInput are called in sequence");
    }

    /** Test to check CtsInputMethod1 isn't current IME. */
    @Test
    public void testIme1IsNotCurrentIme() throws Throwable {
        final TestHelper helper =
                new TestHelper(getClass(), DeviceTestConstants.TEST_IME1_IS_NOT_CURRENT_IME);

        helper.launchActivity(DeviceTestConstants.PACKAGE, DeviceTestConstants.TEST_ACTIVITY_CLASS);
        helper.findUiObject(R.id.text_entry).click();

        pollingCheck(() -> !helper.shell(ShellCommandUtils.getCurrentIme())
                        .equals(Ime1Constants.IME_ID),
                TIMEOUT,
                "CtsInputMethod1 is uninstalled or disabled, and current IME becomes other IME");
    }

    /**
     * Build stream collector of {@link DeviceEvent} collecting sequence that elements have
     * specified types.
     *
     * @param types {@link DeviceEventType}s that elements of sequence should have.
     * @return {@link java.util.stream.Collector} that corrects the sequence.
     */
    private static Collector<DeviceEvent, ?, MatchResult<DeviceEvent>> sequenceOfTypes(
            final DeviceEventType... types) {
        final IntFunction<Predicate<DeviceEvent>[]> arraySupplier = Predicate[]::new;
        return SequenceMatcher.of(Arrays.stream(types)
                .map(DeviceEvent::isType)
                .toArray(arraySupplier));
    }
}
