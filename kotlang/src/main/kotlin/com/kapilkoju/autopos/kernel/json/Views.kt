package com.kapilkoju.autopos.kernel.json

interface Views {

    interface Id

    interface Versioned : Id

    interface DateTimeAudited : Versioned

    interface Summary : DateTimeAudited

}