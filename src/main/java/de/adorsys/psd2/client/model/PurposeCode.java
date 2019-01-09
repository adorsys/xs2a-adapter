/*
 * BG PSD2 API
 * # Summary The **NextGenPSD2** *Framework Version 1.3* offers a modern, open, harmonised and interoperable set of Application Programming Interfaces (APIs) as the safest and most efficient way to provide data securely. The NextGenPSD2 Framework reduces XS2A complexity and costs, addresses the problem of multiple competing standards in Europe and, aligned with the goals of the Euro Retail Payments Board, enables European banking customers to benefit from innovative products and services ('Banking as a Service') by granting TPPs safe and secure (authenticated and authorised) access to their bank accounts and financial data.  The possible Approaches are:   * Redirect SCA Approach   * OAuth SCA Approach   * Decoupled SCA Approach   * Embedded SCA Approach without SCA method   * Embedded SCA Approach with only one SCA method available   * Embedded SCA Approach with Selection of a SCA method    Not every message defined in this API definition is necessary for all approaches.   Furthermore this API definition does not differ between methods which are mandatory, conditional, or optional   Therefore for a particular implementation of a Berlin Group PSD2 compliant API it is only necessary to support   a certain subset of the methods defined in this API definition.    **Please have a look at the implementation guidelines if you are not sure   which message has to be used for the approach you are going to use.**  ## Some General Remarks Related to this version of the OpenAPI Specification: * **This API definition is based on the Implementation Guidelines of the Berlin Group PSD2 API.**   It is not an replacement in any sense.   The main specification is (at the moment) always the Implementation Guidelines of the Berlin Group PSD2 API. * **This API definition contains the REST-API for requests from the PISP to the ASPSP.** * **This API definition contains the messages for all different approaches defined in the Implementation Guidelines.** * According to the OpenAPI-Specification [https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.1.md]      \"If in is \"header\" and the name field is \"Accept\", \"Content-Type\" or \"Authorization\", the parameter definition SHALL be ignored.\"    The element \"Accept\" will not be defined in this file at any place.    The elements \"Content-Type\" and \"Authorization\" are implicitly defined by the OpenApi tags \"content\" and \"security\".  * There are several predefined types which might occur in payment initiation messages,   but are not used in the standard JSON messages in the Implementation Guidelines.   Therefore they are not used in the corresponding messages in this file either.   We added them for the convenience of the user.   If there is a payment product, which need these field, one can easily use the predefined types.   But the ASPSP need not to accept them in general.  * **We omit the definition of all standard HTTP header elements (mandatory/optional/conditional)   except they are mention in the Implementation Guidelines.**   Therefore the implementer might add the in his own realisation of a PSD2 comlient API in addition to the elements define in this file.  ## General Remarks on Data Types  The Berlin Group definition of UTF-8 strings in context of the PSD2 API have to support at least the following characters  a b c d e f g h i j k l m n o p q r s t u v w x y z  A B C D E F G H I J K L M N O P Q R S T U V W X Y Z  0 1 2 3 4 5 6 7 8 9  / - ? : ( ) . , ' +  Space 
 *
 * OpenAPI spec version: 1.3 Dec 20th 2018
 * Contact: info@berlin-group.org
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package de.adorsys.psd2.client.model;

import java.util.Objects;
import io.swagger.v3.oas.annotations.media.Schema;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 * ExternalPurpose1Code from ISO 20022.  Values from ISO 20022 External Code List ExternalCodeSets_1Q2018 June 2018. 
 */
@JsonAdapter(PurposeCode.Adapter.class)
public enum PurposeCode {
  BKDF("BKDF"),
  BKFE("BKFE"),
  BKFM("BKFM"),
  BKIP("BKIP"),
  BKPP("BKPP"),
  CBLK("CBLK"),
  CDCB("CDCB"),
  CDCD("CDCD"),
  CDCS("CDCS"),
  CDDP("CDDP"),
  CDOC("CDOC"),
  CDQC("CDQC"),
  ETUP("ETUP"),
  FCOL("FCOL"),
  MTUP("MTUP"),
  ACCT("ACCT"),
  CASH("CASH"),
  COLL("COLL"),
  CSDB("CSDB"),
  DEPT("DEPT"),
  INTC("INTC"),
  LIMA("LIMA"),
  NETT("NETT"),
  BFWD("BFWD"),
  CCIR("CCIR"),
  CCPC("CCPC"),
  CCPM("CCPM"),
  CCSM("CCSM"),
  CRDS("CRDS"),
  CRPR("CRPR"),
  CRSP("CRSP"),
  CRTL("CRTL"),
  EQPT("EQPT"),
  EQUS("EQUS"),
  EXPT("EXPT"),
  EXTD("EXTD"),
  FIXI("FIXI"),
  FWBC("FWBC"),
  FWCC("FWCC"),
  FWSB("FWSB"),
  FWSC("FWSC"),
  MARG("MARG"),
  MBSB("MBSB"),
  MBSC("MBSC"),
  MGCC("MGCC"),
  MGSC("MGSC"),
  OCCC("OCCC"),
  OPBC("OPBC"),
  OPCC("OPCC"),
  OPSB("OPSB"),
  OPSC("OPSC"),
  OPTN("OPTN"),
  OTCD("OTCD"),
  REPO("REPO"),
  RPBC("RPBC"),
  RPCC("RPCC"),
  RPSB("RPSB"),
  RPSC("RPSC"),
  RVPO("RVPO"),
  SBSC("SBSC"),
  SCIE("SCIE"),
  SCIR("SCIR"),
  SCRP("SCRP"),
  SHBC("SHBC"),
  SHCC("SHCC"),
  SHSL("SHSL"),
  SLEB("SLEB"),
  SLOA("SLOA"),
  SWBC("SWBC"),
  SWCC("SWCC"),
  SWPT("SWPT"),
  SWSB("SWSB"),
  SWSC("SWSC"),
  TBAS("TBAS"),
  TBBC("TBBC"),
  TBCC("TBCC"),
  TRCP("TRCP"),
  AGRT("AGRT"),
  AREN("AREN"),
  BEXP("BEXP"),
  BOCE("BOCE"),
  COMC("COMC"),
  CPYR("CPYR"),
  GDDS("GDDS"),
  GDSV("GDSV"),
  GSCB("GSCB"),
  LICF("LICF"),
  MP2B("MP2B"),
  POPE("POPE"),
  ROYA("ROYA"),
  SCVE("SCVE"),
  SERV("SERV"),
  SUBS("SUBS"),
  SUPP("SUPP"),
  TRAD("TRAD"),
  CHAR("CHAR"),
  COMT("COMT"),
  MP2P("MP2P"),
  ECPG("ECPG"),
  ECPR("ECPR"),
  ECPU("ECPU"),
  EPAY("EPAY"),
  CLPR("CLPR"),
  COMP("COMP"),
  DBTC("DBTC"),
  GOVI("GOVI"),
  HLRP("HLRP"),
  HLST("HLST"),
  INPC("INPC"),
  INPR("INPR"),
  INSC("INSC"),
  INSU("INSU"),
  INTE("INTE"),
  LBRI("LBRI"),
  LIFI("LIFI"),
  LOAN("LOAN"),
  LOAR("LOAR"),
  PENO("PENO"),
  PPTI("PPTI"),
  RELG("RELG"),
  RINP("RINP"),
  TRFD("TRFD"),
  FORW("FORW"),
  FXNT("FXNT"),
  ADMG("ADMG"),
  ADVA("ADVA"),
  BCDM("BCDM"),
  BCFG("BCFG"),
  BLDM("BLDM"),
  BNET("BNET"),
  CBFF("CBFF"),
  CBFR("CBFR"),
  CCRD("CCRD"),
  CDBL("CDBL"),
  CFEE("CFEE"),
  CGDD("CGDD"),
  CORT("CORT"),
  COST("COST"),
  CPKC("CPKC"),
  DCRD("DCRD"),
  DSMT("DSMT"),
  DVPM("DVPM"),
  EDUC("EDUC"),
  FACT("FACT"),
  FAND("FAND"),
  FCPM("FCPM"),
  FEES("FEES"),
  GOVT("GOVT"),
  ICCP("ICCP"),
  IDCP("IDCP"),
  IHRP("IHRP"),
  INSM("INSM"),
  IVPT("IVPT"),
  MCDM("MCDM"),
  MCFG("MCFG"),
  MSVC("MSVC"),
  NOWS("NOWS"),
  OCDM("OCDM"),
  OCFG("OCFG"),
  OFEE("OFEE"),
  OTHR("OTHR"),
  PADD("PADD"),
  PTSP("PTSP"),
  RCKE("RCKE"),
  RCPT("RCPT"),
  REBT("REBT"),
  REFU("REFU"),
  RENT("RENT"),
  REOD("REOD"),
  RIMB("RIMB"),
  RPNT("RPNT"),
  RRBN("RRBN"),
  RVPM("RVPM"),
  SLPI("SLPI"),
  SPLT("SPLT"),
  STDY("STDY"),
  TBAN("TBAN"),
  TBIL("TBIL"),
  TCSC("TCSC"),
  TELI("TELI"),
  TMPG("TMPG"),
  TPRI("TPRI"),
  TPRP("TPRP"),
  TRNC("TRNC"),
  TRVC("TRVC"),
  WEBI("WEBI"),
  ANNI("ANNI"),
  CAFI("CAFI"),
  CFDI("CFDI"),
  CMDT("CMDT"),
  DERI("DERI"),
  DIVD("DIVD"),
  FREX("FREX"),
  HEDG("HEDG"),
  INVS("INVS"),
  PRME("PRME"),
  SAVG("SAVG"),
  SECU("SECU"),
  SEPI("SEPI"),
  TREA("TREA"),
  UNIT("UNIT"),
  FNET("FNET"),
  FUTR("FUTR"),
  ANTS("ANTS"),
  CVCF("CVCF"),
  DMEQ("DMEQ"),
  DNTS("DNTS"),
  HLTC("HLTC"),
  HLTI("HLTI"),
  HSPC("HSPC"),
  ICRF("ICRF"),
  LTCF("LTCF"),
  MAFC("MAFC"),
  MARF("MARF"),
  MDCS("MDCS"),
  VIEW("VIEW"),
  CDEP("CDEP"),
  SWFP("SWFP"),
  SWPP("SWPP"),
  SWRS("SWRS"),
  SWUF("SWUF"),
  ADCS("ADCS"),
  AEMP("AEMP"),
  ALLW("ALLW"),
  ALMY("ALMY"),
  BBSC("BBSC"),
  BECH("BECH"),
  BENE("BENE"),
  BONU("BONU"),
  CCHD("CCHD"),
  COMM("COMM"),
  CSLP("CSLP"),
  GFRP("GFRP"),
  GVEA("GVEA"),
  GVEB("GVEB"),
  GVEC("GVEC"),
  GVED("GVED"),
  GWLT("GWLT"),
  HREC("HREC"),
  PAYR("PAYR"),
  PEFC("PEFC"),
  PENS("PENS"),
  PRCP("PRCP"),
  RHBS("RHBS"),
  SALA("SALA"),
  SSBE("SSBE"),
  LBIN("LBIN"),
  LCOL("LCOL"),
  LFEE("LFEE"),
  LMEQ("LMEQ"),
  LMFI("LMFI"),
  LMRK("LMRK"),
  LREB("LREB"),
  LREV("LREV"),
  LSFL("LSFL"),
  ESTX("ESTX"),
  FWLV("FWLV"),
  GSTX("GSTX"),
  HSTX("HSTX"),
  INTX("INTX"),
  NITX("NITX"),
  PTXP("PTXP"),
  RDTX("RDTX"),
  TAXS("TAXS"),
  VATX("VATX"),
  WHLD("WHLD"),
  TAXR("TAXR"),
  B112("B112"),
  BR12("BR12"),
  TLRF("TLRF"),
  TLRR("TLRR"),
  AIRB("AIRB"),
  BUSB("BUSB"),
  FERB("FERB"),
  RLWY("RLWY"),
  TRPT("TRPT"),
  CBTV("CBTV"),
  ELEC("ELEC"),
  ENRG("ENRG"),
  GASB("GASB"),
  NWCH("NWCH"),
  NWCM("NWCM"),
  OTLC("OTLC"),
  PHON("PHON"),
  UBIL("UBIL"),
  WTER("WTER");

  private String value;

  PurposeCode(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }

  public static PurposeCode fromValue(String text) {
    for (PurposeCode b : PurposeCode.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }

  public static class Adapter extends TypeAdapter<PurposeCode> {
    @Override
    public void write(final JsonWriter jsonWriter, final PurposeCode enumeration) throws IOException {
      jsonWriter.value(enumeration.getValue());
    }

    @Override
    public PurposeCode read(final JsonReader jsonReader) throws IOException {
      String value = jsonReader.nextString();
      return PurposeCode.fromValue(String.valueOf(value));
    }
  }
}
