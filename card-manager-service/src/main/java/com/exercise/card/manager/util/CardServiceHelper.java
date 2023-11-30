package com.exercise.card.manager.util;

import com.exercise.card.manager.core.dto.CustomerDTO;
import com.exercise.card.manager.core.enums.CardCreationStatus;
import com.exercise.card.manager.exception.CardManagerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class CardServiceHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(CardServiceHelper.class);
    private static final String CONTENT_SEPARATOR = ",";
    private static final String FILE_DIR = "cards";
    private static final String FILE_DATE_FORMAT = "yyyyMMdd-HHmmss";
    private static final String TXT_FILE_EXT = ".txt";

    public static void initializeCardCreation(CustomerDTO customerDTO) {
        try {
            write(new File(FILE_DIR, getFileName(customerDTO).concat(TXT_FILE_EXT)), getFileContent(customerDTO));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getFileName(CustomerDTO customerDTO) {
        Date date = new Date();
        String modifiedDate = new SimpleDateFormat(FILE_DATE_FORMAT).format(date);

        StringBuilder fileName = new StringBuilder();
        fileName.append(modifiedDate).append("_").append(customerDTO.getOib());
        return fileName.toString();
    }

    private static String getFileContent(CustomerDTO customerDTO) {
        StringBuilder fileContent = new StringBuilder();
        fileContent.append(customerDTO.getName())
                .append(CONTENT_SEPARATOR)
                .append(customerDTO.getSurname())
                .append(CONTENT_SEPARATOR)
                .append(customerDTO.getOib())
                .append(CONTENT_SEPARATOR)
                .append(CardCreationStatus.ACTIVE.name());
        return fileContent.toString();
    }

    public static void write(final File file, final String line) throws IOException {
        final BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(line);
        writer.flush();
        writer.close();
    }

    public static boolean isCreationStartedForCustomer(CustomerDTO customerDTO) {
        File f = new File(FILE_DIR);
        File[] matchingFiles = f.listFiles((dir, name) -> name.endsWith(customerDTO.getOib().concat(TXT_FILE_EXT)));
        return matchingFiles != null && matchingFiles.length > 0;
    }

    private static Optional<File> getFileByCustomerOib(final String oib) {
        File f = new File(FILE_DIR);
        File[] matchingFiles = f.listFiles((dir, name) -> name.endsWith(oib.concat(TXT_FILE_EXT)));
        if (matchingFiles != null && matchingFiles.length > 0) return Optional.ofNullable(matchingFiles[0]);
        return Optional.empty();
    }

    public static void updateCardCreationStatus(final String oib, final CardCreationStatus cardCreationStatus) {

        Optional<File> optionalFile = getFileByCustomerOib(oib);
        if (optionalFile.isEmpty()) {
            String updateError = "Card creation not started for customer: ".concat(oib);
            LOGGER.error(updateError);
            throw new CardManagerException(updateError, 1030, new String[]{oib});
        }
        File file = optionalFile.get();

        updateFileStatus(file, cardCreationStatus);
    }

    private static void updateFileStatus(File file, CardCreationStatus cardCreationStatus) {

        Optional<String> optionalFileContent;

        try (Stream<String> stream = Files.lines(file.toPath())) {
            optionalFileContent = stream.findFirst();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new CardManagerException("Error getting file", 1031);
        }

        if (optionalFileContent.isEmpty()) {
            LOGGER.error("Error updating file content");
            throw new CardManagerException("Error updating file content", 1032);
        }

        writeContentToFile(file, cardCreationStatus, optionalFileContent.get());
    }

    private static void writeContentToFile(File file, CardCreationStatus cardCreationStatus,
                                           String optionalFileContent) {
        PrintWriter prw;
        try {
            prw = new PrintWriter(file);
            prw.println(updateFileContentStatus(optionalFileContent, cardCreationStatus));
            prw.close();
        } catch (FileNotFoundException e) {
            LOGGER.error(e.getMessage());
            throw new CardManagerException("Error updating card creation status", 1033);
        }
    }

    private static String updateFileContentStatus(String content, CardCreationStatus status) {
        String[] values = content.split(CONTENT_SEPARATOR);
        String v = values[values.length - 1];
        if (!v.equals(status.name())) {
            values[values.length - 1] = status.name();
            return String.join(CONTENT_SEPARATOR, values);
        }
        LOGGER.error("Card creation status already in status: ".concat(status.name()));
        throw new CardManagerException("Card creation status already in status: ".concat(status.name()), 1034);
    }
}
