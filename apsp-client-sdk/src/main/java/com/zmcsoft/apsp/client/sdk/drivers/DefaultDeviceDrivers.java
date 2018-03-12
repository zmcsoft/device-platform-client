package com.zmcsoft.apsp.client.sdk.drivers;

import com.zmcsoft.apsp.client.sdk.drivers.camera.CameraDriver;
import com.zmcsoft.apsp.client.sdk.drivers.multiple.iccard.ICCradDriver;
import com.zmcsoft.apsp.client.sdk.drivers.multiple.idcard.IdentityDriver;
import com.zmcsoft.apsp.client.sdk.drivers.ms.MSIcDriver;
import com.zmcsoft.apsp.client.sdk.drivers.multiple.contact.ContactIcDriver;
import com.zmcsoft.apsp.client.sdk.drivers.multiple.device.MultipleDeviceDriver;
import com.zmcsoft.apsp.client.sdk.drivers.multiple.notcontact.NotContactIcDriver;
import com.zmcsoft.apsp.client.sdk.drivers.printer.PrinterDriver;
import com.zmcsoft.apsp.client.sdk.drivers.softkeyboard.SoftKeyBoardDriver;
import com.zmcsoft.apsp.client.sdk.drivers.voice.VoiceDriver;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhouhao
 * @since 1.0
 */
public class DefaultDeviceDrivers implements DeviceDrivers, DeviceDriverRegister {

    private final Map<Class<? extends DeviceDriver>, Map<String, DeviceDriver>> drivers = new HashMap<>();

    @SuppressWarnings("all")
    private <T extends DeviceDriver> Map<String, T> getProviders(Class<T> type) {
        return (Map<String, T>) drivers.computeIfAbsent(type, (t) -> new HashMap<>());
    }

    private <T extends DeviceDriver> T getDriver(Class<T> type, String provider) {
        T driver = getProviders(type).get(provider);
        if (driver == null) {
            throw new UnsupportedOperationException("找不到设备驱动:" + provider);
        }
        return driver;
    }

    @Override
    public CameraDriver camera() {
        return getDriver(CameraDriver.class, DEFAULT_DRIVER_PROVIDER);
    }

    @Override
    public PrinterDriver printer() {
        return getDriver(PrinterDriver.class, DEFAULT_DRIVER_PROVIDER);
    }

    @Override
    public VoiceDriver voice() {
        return getDriver(VoiceDriver.class, DEFAULT_DRIVER_PROVIDER);
    }

    @Override
    public SoftKeyBoardDriver softKeyBoard() {
        return getDriver(SoftKeyBoardDriver.class, DEFAULT_DRIVER_PROVIDER);
    }

    protected String checkProvider(String provider) {
        if (null == provider || provider.isEmpty()) {
            return DEFAULT_DRIVER_PROVIDER;
        }
        return provider;
    }

    @Override
    public CameraDriver camera(String provider) {
        return getDriver(CameraDriver.class, checkProvider(provider));
    }

    @Override
    public PrinterDriver printer(String provider) {
        return getDriver(PrinterDriver.class, checkProvider(provider));
    }

    @Override
    public SoftKeyBoardDriver softKeyBoard(String provider) {
        return getDriver(SoftKeyBoardDriver.class, checkProvider(provider));
    }

    @Override
    public VoiceDriver voice(String provider) {
        return getDriver(VoiceDriver.class, checkProvider(provider));
    }

    @Override
    public IdentityDriver identity() {
        return getDriver(IdentityDriver.class, DEFAULT_DRIVER_PROVIDER);
    }

    @Override
    public IdentityDriver identity(String provider) {
        return getDriver(IdentityDriver.class, checkProvider(provider));
    }

    @Override
    public MSIcDriver mSIcCard() {
        return getDriver(MSIcDriver.class, DEFAULT_DRIVER_PROVIDER);
    }

    @Override
    public MSIcDriver mSIcCard(String provider) {
        return getDriver(MSIcDriver.class, checkProvider(provider));
    }

    @Override
    public MultipleDeviceDriver multipleDeviceDriver() {
        return getDriver(MultipleDeviceDriver.class, DEFAULT_DRIVER_PROVIDER);
    }

    @Override
    public MultipleDeviceDriver multipleDeviceDriver(String provider) {
        return getDriver(MultipleDeviceDriver.class, checkProvider(provider));
    }

    @Override
    public ContactIcDriver contactIcDriver() {
        return getDriver(ContactIcDriver.class, DEFAULT_DRIVER_PROVIDER);
    }

    @Override
    public ContactIcDriver contactIcDriver(String provider) {
        return getDriver(ContactIcDriver.class, checkProvider(provider));
    }

    @Override
    public NotContactIcDriver notContactIcDriver() {
        return getDriver(NotContactIcDriver.class, DEFAULT_DRIVER_PROVIDER);
    }

    @Override
    public NotContactIcDriver notContactIcDriver(String provider) {
        return getDriver(NotContactIcDriver.class, checkProvider(provider));
    }

    @Override
    public ICCradDriver iCCradDriver() {
        return getDriver(ICCradDriver.class, DEFAULT_DRIVER_PROVIDER);
    }

    @Override
    public ICCradDriver iCCradDriver(String provider) {
        return getDriver(ICCradDriver.class, checkProvider(provider));
    }

    @Override
    public <T extends DeviceDriver> void register(Class<T> type, T driver, String provider) {
        getProviders(type).put(provider, driver);
    }

    @Override
    public <T extends DeviceDriver> T unregister(Class<T> type, String provider) {
        return getProviders(type).remove(provider);
    }
}
