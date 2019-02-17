package com.joh.cms.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joh.cms.dao.ReportDAO;
import com.joh.cms.domain.model.DoctorCustomerOrderD;
import com.joh.cms.domain.model.NotificationD;

@Service
public class ReportServiceImpl implements ReportService {
	@Autowired
	private ReportDAO reportDAO;

	@Override
	public List<DoctorCustomerOrderD> findDoctorCustomerOrder(int doctorId,Date from, Date to) {
		return reportDAO.findDoctorCustomerOrder(doctorId,from, to);
	}
	
	
	@Override
	public List<NotificationD> findAdminNotifications() {
		return reportDAO.findAdminNotifications();
	}
	

}
