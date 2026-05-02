package voz.incomingbill.ai.dto;

import java.util.List;

public record ScanDocument(String fileName, List<Good> goods) {
}
