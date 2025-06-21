package com.yadav.journalApp.entity;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Document(collection = "journal_entries")
@Data // Generates getters, setters, toString, equals, hashCode
@NoArgsConstructor // No-arg constructor
@AllArgsConstructor // All-arg constructor
public class JournalEntry {

	@Id
	private ObjectId id;
	private String userName;
	private String title;
	private String content;
	private LocalDateTime date;
}
