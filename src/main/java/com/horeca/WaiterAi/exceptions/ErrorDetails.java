package com.horeca.WaiterAi.exceptions;

import java.util.Date;


public record ErrorDetails(Date timestamp, String errorCode, String message, String status, Boolean succes) {
}
