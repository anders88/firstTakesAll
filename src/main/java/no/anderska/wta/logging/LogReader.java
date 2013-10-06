package no.anderska.wta.logging;

import no.anderska.wta.dto.AnswerLogEntryDTO;
import no.anderska.wta.dto.LogEntryDetailDTO;

import java.util.List;

public interface LogReader {
    List<AnswerLogEntryDTO> getLogEntries();
    List<LogEntryDetailDTO> getDetail(long id);
}
