package com.zcitc.cloudhousehelper.utils

import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession

private class MyHostnameVerifier : HostnameVerifier {
    override fun verify(hostname: String?, session: SSLSession?): Boolean {
        return  true
    }

}
