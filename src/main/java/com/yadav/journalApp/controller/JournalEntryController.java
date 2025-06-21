package com.yadav.journalApp.controller;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yadav.journalApp.entity.JournalEntry;
import com.yadav.journalApp.entity.User;
import com.yadav.journalApp.service.JournalEntryService;
import com.yadav.journalApp.service.UserService;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
	
	@Autowired
	private JournalEntryService journalEntryService;
	
	@Autowired
	private UserService userService;
	
//	@GetMapping
//	public List<JournalEntry>getAll(){
//		return journalEntryService.getAll();
//	}
	@GetMapping("{userName}")
	public ResponseEntity<?>getAllJournalEntriesOfUser(@PathVariable String userName)
	{
		User user=userService.findByUserName(userName);
		List<JournalEntry>all=user.getJournalEntries();
		if(all!=null && !all.isEmpty())
		{
			return new ResponseEntity<>(all,HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("{userName}")
	public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry journalEntry,@PathVariable String userName)
	{
		try {
			journalEntryService.saveEntry(journalEntry,userName);
			return new ResponseEntity<JournalEntry>(journalEntry, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<JournalEntry>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("id/{myId}")
	public ResponseEntity<JournalEntry>getJournalEntryById(@PathVariable ObjectId myId)
	{
		 Optional<JournalEntry>journalEntry=journalEntryService.getById(myId);
		 if(journalEntry.isPresent())
		 {
			 return new ResponseEntity<>(journalEntry.get(),HttpStatus.OK);
		 }
		 else {
			 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		 }
	}
	
	@DeleteMapping("id/{userName}/{myId}")
	public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId,@PathVariable String userName)
	{
		journalEntryService.deleteById(myId,userName);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PutMapping("id/{userName}/{id}")
	public ResponseEntity<JournalEntry> updateJournalEntryById(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry,@PathVariable String userName) {
	    Optional<JournalEntry> optionalOld = journalEntryService.getById(id);
	    
	    if (optionalOld.isPresent()) {
	        JournalEntry old = optionalOld.get();
	        old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : old.getTitle());
	        old.setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : old.getContent());
	        
	        journalEntryService.saveEntry(old);
	        return new ResponseEntity<>(old, HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	}

}
