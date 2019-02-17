package com.joh.cms.service;

import java.util.Date;
import java.util.List;

import com.joh.cms.domain.model.DoctorCustomerOrderD;
import com.joh.cms.domain.model.NotificationD;

public interface ReportService {

	List<DoctorCustomerOrderD> findDoctorCustomerOrder(int doctorId, Date from, Date to);

	List<NotificationD> findAdminNotifications();

}
