package no.anderska.wta.logging;

import no.anderska.wta.dto.AnswerLogEntryDTO;

import java.util.List;

public interface LogReader {
    List<AnswerLogEntryDTO> getLogEntries();
}
