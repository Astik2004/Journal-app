package com.yadav.journalApp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yadav.journalApp.entity.JournalEntry;
import com.yadav.journalApp.entity.User;
import com.yadav.journalApp.respository.JournalEntryRepository;
import org.springframework.transaction.annotation.Transactional;

@Component
public class JournalEntryService {
	
	@Autowired
	private JournalEntryRepository journalEntryRepository;
	@Autowired
	private UserService userService;
	@Transactional
	public void saveEntry(JournalEntry journalEntry,String userName)
	{
		try {
			User user=userService.findByUserName(userName);
			journalEntry.setDate(LocalDateTime.now());
			JournalEntry saved=journalEntryRepository.save(journalEntry);
			user.getJournalEntries().add(saved);
			userService.saveEntry(user);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void saveEntry(JournalEntry journalEntry)
	{
		
		journalEntryRepository.save(journalEntry);
	}
	
	public List<JournalEntry>getAll(){
		return journalEntryRepository.findAll();
	}
	
	public Optional<JournalEntry> getById(ObjectId id)
	{
		return journalEntryRepository.findById(id);
	}
	public void deleteById(ObjectId id, String userName) {
		User user = userService.findByUserName(userName);
		if (user == null) {
			throw new RuntimeException("User not found with username: " + userName);
		}
		if (user.getJournalEntries() != null) {
			user.getJournalEntries().removeIf(x -> x.getId().equals(id));
			userService.saveEntry(user);
		}
		journalEntryRepository.deleteById(id);
	}
}
