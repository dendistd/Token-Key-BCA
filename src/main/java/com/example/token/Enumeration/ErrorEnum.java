package com.example.token.Enumeration;

public enum ErrorEnum {
	SUCCESS("IKSEI-00-000", "Berhasil", "Success"),
	SUCCESS_EAI("ESB-00-000", "Berhasil", "Success"),
	SUCCESS_RESPONSE_0("IKSEI-00-001", "Berhasil, Dengan KSEI Response 0", "Success, With KSEI Response 0"),
	FAIL_ACCESS_ACCOUNT_REPORT_SERVICE("IKSEI-00-002", "Koneksi Ke Service Account Report Gagal", "Connection To Account Report Service Fail"),
	INQUIRY_FAILED("IKSEI-99-000", "Gagal", "Fail"),
	INQUIRY_FAILED_SRE_RDN("IKSEI-99-001", "Gagal, SRE & RDN Sudah Terdaftar Dengan Status Valid", "Fail, SRE & Account Number Has Been Registered With Valid Status"),
	INQUIRY_FAILED_SRE("IKSEI-99-002", "Gagal, SRE Sudah Terdaftar Dengan Status Valid", "Fail, SRE Has Been Registered With Valid Status"),
	INQUIRY_FAILED_RDN("IKSEI-99-003", "Gagal, RDN Sudah Terdaftar Dengan Status Valid", "Fail, Account Number Has Been Registered With Valid Status"),
	VALIDATION_FAILED("IKSEI-99-004", "Data Sudah Pernah Berhasil Di Lapor Buka Sebelumnya", "Data Has Been Successfully Registered Before"),
	SP_VALIDATION_FAIL("IKSEI-99-005", "Data Ditolak Verifikasi SP", "Data Rejected By SP Validation"),
	FAIL_SAME_ACCOUNT_STATUS_SEND("IKSEI-99-006", "Tidak Bisa Mengupdate Account Status Dengan Status Yang Sama", "Can't Update Account Status With Same Status"),
	FAIL_ACTION_NOT_04("IKSEI-99-007", "Update Hanya Dapat Dilakukan Kepada Account Dengan Nilai Action 04", "Update Can Only Be Applied to Account With Action 04"),
	FAIL_ACTION_STATUS_NOT_01("IKSEI-99-008", "Update Hanya Bisa Dilakukan kepada Account Dengan Nilai StatusAction 01", "Update Can Only Be Applied to Account With StatusAction 01"),
	FAIL_ACCOUNT_NOT_VERIFIED_BY_KSEI("IKSEI-99-009", "Update Hanya Dapat Dilakukan Kepada Account Yang Sudah Diverifikasi KSEI", "Update Can Only Be Applied to Account That Already Verified By KSEI"),
	FAIL_UPDATE_OPEN_WITH_UNBLOCK("IKSEI-99-010", "Tidak Dapat Mengganti Status BUKA Dengan BUKA BLOKIR", "Can't Unblock Account With Account Status OPEN"),
	FAIL_UPDATE_CLOSE_ACCOUNT("IKSEI-99-011", "Tidak Dapat Mengupdate Akun Yang Memiliki Status TUTUP", "Can't Update Status CLOSE Account"),
	FAIL_UPDATE_CLOSED_ACCOUNT_WITH_OPEN("IKSE-99-012", "Tidak Dapat Mengganti Status BLOKIR Dengan Status Buka", "Can't Update Blocked Account With Open Status"),
	NO_RESULT_FOUND("IKSEI-99-013", "Data Tidak Dapat Ditemukan", "Data Not Found"),
	UPDATE_ON_PROGRESS("IKSEI-99-014", "Data Yang Dimasukan Sedang Dalam Proses Validasi Lapor Akun KSEI", "Inserted Data Still In KSEI Account Report Validation Proses"),
	DATE_FILTER_NOT_COMPLETE("IKSEI-99-015", "Tanggal Pencarian Tidak Lengkap", "Search Date Filter Option Not Complete"),
	KEY_OPTION_NOT_AVAILABLE("IKSEI-99-016", "Opsi Kunci Pencarian Tidak Tersedia", "Search Key Option Not Available"),
	REJECT_RETRY_FOR_VALID_DATA("IKSEI-99-017", "Kirim Ulang Account Report Tidak Bisa Dilakukan Untuk Data Valid", "Can't Retry Account Report For Valid Data"),
	RDN_LENGTH_NUMERIC_10("IKSEI-99-018", "Panjang No RDN Harus Numerik 10 Digit", "RDN Account Length Must Be Numeric 10 Digit"),
	AB_ID_NOT_FOUND("IKSEI-99-019", "Kode AB Tidak Ditemukan Pada Database IKSEI", "AB ID Not Found In IKSEI Database"),
	MANDATORY_FIELD("IKSEI-99-020", "Data Tidak Lengkap", "Mandatory Field Should Be Filled"),
	NOTIF_AB_NOT_FOUND("IKSEI-99-021", "Data Notifikasi AB Tidak Ditemukan", "AB Notification Data Not Found"),
	NOTIF_VALIDATION_PROBLEM("IKSEI-99-022", "Validasi Notifikasi AB Gagal", "Fail Validating AB Notification"),
	NOTIF_RECON_INVALID("IKSEI-99-023", "Rekonsiliasi Notif AB Tidak Bisa Dilakukan", "AB Notification Reconciliation Can't Be Done"),
	QUERY_FAILED("IKSEI-99-990", "Query Mendapatkan Hasil Gagal", "Query Return Fail"),
	INVALID_BODY_REQUEST("IKSEI-99-991", "Isi body tidak sesuai", "Invalid body request"),
	INVALID_FIELD_VALUE("IKSEI-99-992", "Isi field tidak sesuai", "Invalid field value"),
	UNKNOWN_ERROR("IKSEI-99-999", "Sistem tidak tersedia", "System Not Available"),
	DELETE("IKSEI-22-000", "Hapus Data Berhasil", "Delete Data Success"),
	FAIL_DELETE("IKSEI-22-111", "Hapus Data Gagal", "Delete Data Failed"),
	CREATE("IKSEI-33-000", "Data Berhasil Ditambahkan", "Insert Data Success"),
	FAIL_CREATE("IKSEI-33-111", "Data Gagal Ditambahkan", "Insert Data Failed"),
	GET_ALL("IKSEI-44-000", "Berhasil Menampilkan Semua Data", "Success TO Show All Data"),
	FAIL_GET_ALL("IKSEI-44-111", "Gagal Menampilkan Semua Data", "Failed To Show All Data" ),
	UPDATE("IKSEI-55-000", "Berhasil Memperbaharui Data", "Success To Update Data"),
	FAIL_UPDATE("IKSEI-55-111", "Gagal Memperbaharui Data", "Failed To Update Data"),
	FIND_ONE("IKSEI-66-000", "Berhasil Menemukan Data", "Find Data Success"),
	FAIL_FIND_ONE("IKSEI-66-111", "Gagal Menemukan Data", "Failed To Find Data");
	

	private String errorCode;
	private String errorMessageIndonesian;
	private String errorMessageEnglish;
	
	private ErrorEnum(String errorCode, String errorMessageIndonesian, String errorMessageEnglish) {
		this.errorCode = errorCode;
		this.errorMessageIndonesian = errorMessageIndonesian;
		this.errorMessageEnglish = errorMessageEnglish;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessageIndonesian() {
		return errorMessageIndonesian;
	}
	public void setErrorMessageIndonesian(String errorMessageIndonesian) {
		this.errorMessageIndonesian = errorMessageIndonesian;
	}
	public String getErrorMessageEnglish() {
		return errorMessageEnglish;
	}
	public void setErrorMessageEnglish(String errorMessageEnglish) {
		this.errorMessageEnglish = errorMessageEnglish;
	}

}
