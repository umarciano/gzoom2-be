package it.mapsgroup.gzoom.querydsl.dto;

public class PartyNoteEx extends PartyNote {
	private NoteData noteData;
	
	/**
	 * @return the noteData
	 */
	public NoteData getNoteData() {
		return noteData;
	}
	/**
	 * @param noteData the noteData to set
	 */
	public void setNoteData(NoteData noteData) {
		this.noteData = noteData;
	}
}
