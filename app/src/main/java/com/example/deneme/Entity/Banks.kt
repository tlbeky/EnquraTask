package com.example.deneme.Entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Banks(@SerializedName("ID")
                 @Expose
                 var ID: Int,
                 @SerializedName("dc_SEHIR")
                 @Expose
                 var dcSEHIR: String,
                 @SerializedName("dc_ILCE")
                 @Expose
                 var dcILCE: String,
                 @SerializedName("dc_BANKA_SUBE")
                 @Expose
                 var dcBANKASUBE: String,
                 @SerializedName("dc_BANKA_TIPI")
                 @Expose
                 var dcBANKATIPI: String,
                 @SerializedName("dc_BANK_KODU")
                 @Expose
                 var dcBANKKODU: String,
                 @SerializedName("dc_ADRES_ADI")
                 @Expose
                 var dcADRESADI: String,
                 @SerializedName("dc_ADRES")
                 @Expose
                 var dcADRES: String,
                 @SerializedName("dc_POSTA_KODU")
                 @Expose
                 var dcPOSTAKODU: String,
                 @SerializedName("dc_ON_OFF_LINE")
                 @Expose
                 var dcONOFFLINE: String,
                 @SerializedName("dc_ON_OFF_SITE")
                 @Expose
                 var dcONOFFSITE: String,
                 @SerializedName("dc_BOLGE_KOORDINATORLUGU")
                 @Expose
                 var dcBOLGEKOORDINATORLUGU: String,
                 @SerializedName("dc_EN_YAKIM_ATM")
                 @Expose
                 var dcENYAKIMATM: String) {


}


