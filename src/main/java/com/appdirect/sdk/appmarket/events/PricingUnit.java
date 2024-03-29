/*
 * Copyright 2017 AppDirect, Inc. and/or its affiliates
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.appdirect.sdk.appmarket.events;

/**
 * Represents the pricing unit, currently in a {@link OrderInfo}
 */
public enum PricingUnit {
	USER,
	GIGABYTE,
	MEGABYTE,
	HOUR,
	MINUTE,
	INVOICE,
	UNIT,
	PROJECT,
	PROPERTY,
	ITEM,
	WORD,
	EMAIL,
	CONTACT,
	CALL,
	CREDIT,
	ROOM,
	HOST,
	AGENT,
	OPERATOR,
	PROVIDER,
	MANAGER,
	TESTER,
	JVM,
	SERVER,
	WEB_USE_MINUTE,
	AUDIO_USE_MINUTE,
	PIECE,
	EMPLOYEE_PAY_PERIOD,
	EMPLOYEE_PER_PAY_PERIOD,
	COMPUTER,
	NOT_APPLICABLE,
	ONE_TIME_SETUP,
	DATA_POINTS,
	TIER1_API_CALLS,
	TIER2_API_CALLS,
	ADVISORY_HOURS,
	OVERAGE_AUDIO_MINUTE,
	EMPLOYEE,
	CONNECTION,
	PUSH_USER,
	THOUSAND_EMAILS_PER_DAY,
	PUSH_NOTIFICATION_DEVICES,
	API_CALLS,
	SMS_TEXT_MESSAGE,
	CONTACTS_1000,
	CONTRACT_FEE,
	TRANSFER_FEE,
	REACTIVATION_FEE,
	RECIPIENT,
	ADDITIONAL_1000_CONTACTS_BLOCK,
	SCHEDULE_PLAN,
	EMAILS_1000,
	EMAILS_2500,
	MOBILE_DEVICE,
	PAYSLIP,
	PAYSLIP_CORRECTION,
	STORE,
	WEBSITE,
	EPAPER,
	PAGE,
	POSTAGE_AND_PRINT,
	INTERNATIONAL_POSTAGE_AND_PRINT,
	TIER1_TOP_LEVEL_DOMAIN,
	TIER2_TOP_LEVEL_DOMAIN,
	DEDICATED_IP,
	ENABLELCM,
	MAXCOMPONENTS,
	DATA_MANAGEMENT_USER,
	SPECIALIST_USER,
	PROFESSIONAL_USER,
	MATERIALITY_MATRIX,
	STAKEHOLDER_MANAGEMENT,
	SCORECARD,
	STANDARD_MAPPING,
	DONATION_MANAGEMENT,
	DOCUMENT,
	PACKAGE_SMALL,
	PACKAGE_LARGE,
	MEMBER,
	ATTENDEE,
	MAILING,
	RESPONSE,
	EXTERNAL_INVOICE_FEE,
	CLIENT_TEST,
	IMAGE_TRANSFORMATION,
	TOTAL_IMAGE,
	LICENSE,
	MAILBOX,
	FREE_40_INCH_HDTV_PC,
	FREE_46_INCH_HDTV_PC,
	FREE_46_INCH_HDTV_PC_MOUNTING,
	EMPLOYEE_PER_WEEK,
	REGISTER,
	END_USER,
	CORE,
	DEVICE,
	PORT,
	MEASURER,
	PUBLISHED_MEASUREMENT,
	NODE,
	SERVER_RULE,
	VPN_LP,
	PROXY_LP,
	DESKTOP_CONNECT_LP,
	CAMERA,
	MAIN_SOUND_ZONE,
	SUB_SOUND_ZONE,
	POST,
	REPORT,
	BOX,
	SESSION,
	DISPLAY,
	TRUCKROLL,
	TRANSACTION_FEE,
	SENDING_API_CALL,
	LOOKUP_API_CALL,
	ANALYTICS_API_CALL,
	MIGRATION_INSTANCE,
	NFON_SETUP_PER_PHONE_EXTENSION,
	NFON_SETUP_PER_PHONE_EXTENSION_PLUS,
	NFON_SETUP_PER_EFAX_EXTENSION,
	NFON_PHONE_EXTENSION,
	NFON_PHONE_EXTENSION_PLUS,
	NFON_EFAX_EXTENSION,
	NFON_CALL_CENTER_MONITORING,
	NFON_NMEETING,
	NFON_MOBILE_NFON_DEVICE,
	NFON_ISOFTPHONE_MAC,
	NFON_NSOFTPHONE_STANDARD_WINDOWS,
	NFON_NSOFTPHONE_PREMIUM_WINDOWS,
	NFON_NCTI_STANDARD_WINDOWS,
	NFON_NCTI_STANDARD_CRM_WINDOWS,
	NFON_NCTI_STANDARD_MAC,
	NFON_NSOFTPHONE_STANDARD_WINDOWS_OR_MOBILE,
	WESUSTAIN_PERFORMANCE,
	WESUSTAIN_STAKEHOLDER_REPUTATION,
	WESUSTAIN_WEAPP,
	FAX,
	FAX_LINE,
	ROOM_LINE,
	DEPARTMENT_LINE,
	INTERNATIONAL_LICENSE,
	INTERNATIONAL_DEPARTMENT_LINE,
	INTERNATIONAL_ROOM_LINE,
	INTERNATIONAL_LINE,
	CALLING_CREDIT,
	LINE,
	TOLLFREE_ROOM_LINE,
	TOLLFREE_DEPARTMENT_LINE,
	TAXES_AND_FEES,
	LEAD,
	OPPORTUNITY,
	CAMPAIGN,
	CASE,
	CUSTOMER,
	TIER1_STANDARD_LINE,
	TIER1_ROOM_LINE,
	TIER1_TOLLFREE_ROOM_LINE,
	TIER1_TOLLFREE_DEPARTMENT_LINE,
	TIER1_FAX_LINE,
	TIER1_DEPARTMENT_LINE,
	TIER2_STANDARD_LINE,
	TIER2_ROOM_LINE,
	TIER2_DEPARTMENT_LINE,
	TIER3_STANDARD_LINE,
	TIER3_ROOM_LINE,
	TIER3_DEPARTMENT_LINE,
	TIER4_STANDARD_LINE,
	TIER4_ROOM_LINE,
	TIER4_DEPARTMENT_LINE,
	CLUSTER,
	NODE_4VCPU,
	FIVE_HUNDRED_GB_SSD,
	TWELVE_TB_NETWORK_IO,
	JBOSS_EAP,
	JBOSS_FUSE,
	JBOSS_A_MQ,
	JBOSS_BRMS,
	JBOSS_BPM_SUITE,
	JBOSS_DATA_GRID,
	JBOSS_DATA_VIRT,
	USER_LICENSE,
	ADDITIONAL_NUMBER_LICENSE,
	ROOM_PHONE_LICENSE,
	UBERCONFERENCE_PRO_LICENSE,
	UBERCONFERENCE_PRO_LICENSE_UNBUNDLED,
	INSTANCE,
	INDOOR_CAMERA,
	OUTDOOR_CAMERA,
	VINGATE_LP_LICENCE,
	ADMINISTRATOR,
	MOBILE_USER,
	T1,
	PHONE,
	AUTO_ATTENDANT,
	HUNT_GROUP,
	VOICEMAIL_BOX,
	TOLL_FREE_NUMBER,
	INBOUND_LONG_DISTANCE_MINUTE,
	OUTBOUND_LONG_DISTANCE_MINUTE,
	INBOUND_OUTBOUND_LONG_DISTANCE_MINUTE,
	SET_TOP_BOX,
	MODEM,
	ACCESS_POINT,
	CALLING_FEATURE,
	IAD_DEVICE,
	ANALOG_LINE,
	PRI,
	SBC_DEVICE,
	ROUTER,
	INTERNATIONAL_MINUTE,
	PHONE_LINE,
	DYNAMIC_IP_ADDRESS,
	STATIC_IP_ADDRESS,
	GATEWAY,
	REMOTE_CONTROL,
	TIER1_STANDARD_SEAT,
	TIER2_STANDARD_SEAT,
	TIER3_STANDARD_SEAT,
	TIER4_STANDARD_SEAT,
	TIER1_TOLL_FREE_SEAT,
	MILLION_MESSAGES,
	MESSAGE,
	WEEKLY,
	PHONE_NUMBER,
	GUEST,
	REVISION_SAFE_DATAROOM,
	GROUP_SESSION,
	MAILBOX_AD_SYNC,
	PHONE_SUPPORT,
	EXTRA_10TB,
	EXTRA_50TB,
	EXTRA_200TB,
	MAP_VIEW,
	SVM,
	CHANNEL,
	NUMBER_PRINTER_TABLET,
	NUMBER_ONSITE_SERVER,
	NUMBER_VISITS,
	ONSITE_TECHNICIAN_PC,
	ONSITE_TECHNICIAN_SERVER,
	SHORTER_REACTION_TIMES,
	E_COMMERCE_INTEGRATION,
	LOCATION,
	TRAINING_SESSION,
	PRORATED_CREDIT,
	PRIVACY,
	ADDON_LICENSE
}
