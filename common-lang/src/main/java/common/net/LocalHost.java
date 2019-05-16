package common.net;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Pattern;

public class LocalHost {

    private static final Logger logger = LoggerFactory.getLogger(LocalHost.class);

    private static final Supplier<LocalHost> supplier = Suppliers.memoize(() -> new LocalHost());

    private static final String LOCALHOST = "127.0.0.1";
    private static final String ANYHOST = "0.0.0.0";
    private final Pattern IP_PATTERN = Pattern.compile("\\d{1,3}(\\.\\d{1,3}){3,5}$");

    private final List<InetAddress> all;
    private final List<Inet4Address> loopV4;
    private final List<Inet6Address> loopV6;
    private final List<Inet4Address> nonLoopV4;
    private final List<Inet6Address> nonLoopV6;


    private LocalHost() {
        List<InetAddress> _all = new ArrayList<>();
        List<Inet4Address> _loopV4 = new ArrayList<>();
        List<Inet6Address> _loopV6 = new ArrayList<>();
        List<Inet4Address> _noLoopV4 = new ArrayList<>();
        List<Inet6Address> _noLoopV6 = new ArrayList<>();

        try {
            Enumeration<NetworkInterface> eif = NetworkInterface.getNetworkInterfaces();

            while (eif.hasMoreElements()) {
                for (InterfaceAddress ia : eif.nextElement().getInterfaceAddresses()) {
                    InetAddress addr = ia.getAddress();
                    _all.add(addr);

                    if (addr.isLoopbackAddress()) {
                        if (addr instanceof Inet4Address) {
                            _loopV4.add((Inet4Address) addr);
                        } else {
                            _loopV6.add((Inet6Address) addr);
                        }
                    } else {
                        if (addr instanceof Inet4Address) {
                            _noLoopV4.add((Inet4Address) addr);
                        } else {
                            _noLoopV6.add((Inet6Address) addr);
                        }
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

        all = Collections.unmodifiableList(_all);
        loopV4 = Collections.unmodifiableList(_loopV4);
        loopV6 = Collections.unmodifiableList(_loopV6);
        nonLoopV4 = Collections.unmodifiableList(_noLoopV4);
        nonLoopV6 = Collections.unmodifiableList(_noLoopV6);
    }

    private boolean isValid(InetAddress address) {
        if (address == null || address.isLoopbackAddress()) {
            return false;
        }

        String name = address.getHostAddress();
        return name != null
                && !ANYHOST.equals(name)
                && !LOCALHOST.equals(name)
                && IP_PATTERN.matcher(name).matches();

    }

    private InetAddress getLocalAddress0() {

        try {
            InetAddress address = InetAddress.getLocalHost();

            if (isValid(address)) {
                return address;
            }

            Enumeration<NetworkInterface> nif = NetworkInterface.getNetworkInterfaces();
            while (nif.hasMoreElements()) {
                for (InterfaceAddress ia : nif.nextElement().getInterfaceAddresses()) {
                    InetAddress addr = ia.getAddress();
                    if (isValid(addr)) {
                        return addr;
                    }
                }
            }
        } catch (Exception e) {
            logger.warn("查询IP地址失败", e);
        }

        return null;
    }

    private static volatile InetAddress LOCAL_ADDRESS = null;

    public static List<InetAddress> getAll() {
        return get().all;
    }

    public static List<Inet4Address> getLoopV4() {
        return get().loopV4;
    }

    public static List<Inet6Address> getLoopV6() {
        return get().loopV6;
    }

    public static List<Inet4Address> getNonLoopV4() {
        return get().nonLoopV4;
    }

    public static List<Inet6Address> getNonLoopV6() {
        return get().nonLoopV6;
    }

    public static LocalHost get() {
        return supplier.get();
    }

    public static InetAddress getLocalAddress() {

        if (LOCAL_ADDRESS == null) {
            LOCAL_ADDRESS = get().getLocalAddress0();
        }

        return LOCAL_ADDRESS;
    }

    public static String getLocalHost() {
        InetAddress address = getLocalAddress();
        return address == null ? LOCALHOST : address.getHostAddress();
    }
}
