package com.zcitc.cloudhousehelper.utils

import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import java.net.URLEncoder
object UrlComponentUtils{
        fun  encodeURIComponent(component: String): String? {
        var result: String? = null
        try {
            result = URLEncoder.encode(component, "UTF-8")
                    .replace("\\%28".toRegex(), "(")
                    .replace("\\%29".toRegex(), ")")
                    .replace("\\+".toRegex(), "%20")
                    .replace("\\%27".toRegex(), "'")
                    .replace("\\%21".toRegex(), "!")
                    .replace("\\%7E".toRegex(), "~")

        } catch (e: UnsupportedEncodingException) {
            result = component
        }
        return result
    }
    fun  decodeURIComponent(component: String): String? {
        var result: String? = null
        try {
            result = URLDecoder.decode(component, "UTF-8")
                    .replace("\\%28".toRegex(), "(")
                    .replace("\\%29".toRegex(), ")")
                    .replace("\\+".toRegex(), "%20")
                    .replace("\\%27".toRegex(), "'")
                    .replace("\\%21".toRegex(), "!")
                    .replace("\\%7E".toRegex(), "~")

        } catch (e: UnsupportedEncodingException) {
            result = component
        }
        return result
    }
}
