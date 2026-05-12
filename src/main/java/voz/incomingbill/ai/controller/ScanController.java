package voz.incomingbill.ai.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import voz.incomingbill.ai.service.agent.JpegAiAgentService;
import voz.incomingbill.ai.service.agent.PdfAiAgentService;
import voz.incomingbill.ai.service.agent.XmlAiAgentService;

@RestController
@Slf4j
@RequestMapping("api/scans")
public class ScanController {
    @Autowired
    private JpegAiAgentService jpegAiAgentService;
    @Autowired
    private PdfAiAgentService pdfAiAgentService;
    @Autowired
    private XmlAiAgentService xmlAiAgentService;


    @GetMapping("process/jpg")
    public String executeAiAgentForJpg() {
        return jpegAiAgentService.process();
    }

    @GetMapping("process/pdf")
    public String executeAiAgentForPdf() {
        return pdfAiAgentService.process();
    }

    @GetMapping("process/xml")
    public String executeAiAgentForXml() {
        return xmlAiAgentService.process();
    }

    @GetMapping("test")
    public String getTest() {
        return jpegAiAgentService.testConnection();
    }
}
