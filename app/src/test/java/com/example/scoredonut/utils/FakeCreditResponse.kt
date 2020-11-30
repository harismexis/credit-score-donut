package com.example.scoredonut.utils

fun getCreditResponseValid(): String {
    return "{\n" +
            "  \"accountIDVStatus\": \"PASS\",\n" +
            "  \"creditReportInfo\": {\n" +
            "    \"score\": 327,\n" +
            "    \"scoreBand\": 4,\n" +
            "    \"clientRef\": \"CS-SED-655426-708782\",\n" +
            "    \"status\": \"MATCH\",\n" +
            "    \"maxScoreValue\": 700,\n" +
            "    \"minScoreValue\": 0,\n" +
            "    \"monthsSinceLastDefaulted\": -1,\n" +
            "    \"hasEverDefaulted\": false,\n" +
            "    \"monthsSinceLastDelinquent\": 1,\n" +
            "    \"hasEverBeenDelinquent\": true,\n" +
            "    \"percentageCreditUsed\": 44,\n" +
            "    \"percentageCreditUsedDirectionFlag\": 1,\n" +
            "    \"changedScore\": 0,\n" +
            "    \"currentShortTermDebt\": 13758,\n" +
            "    \"currentShortTermNonPromotionalDebt\": 13758,\n" +
            "    \"currentShortTermCreditLimit\": 30600,\n" +
            "    \"currentShortTermCreditUtilisation\": 44,\n" +
            "    \"changeInShortTermDebt\": 549,\n" +
            "    \"currentLongTermDebt\": 24682,\n" +
            "    \"currentLongTermNonPromotionalDebt\": 24682,\n" +
            "    \"currentLongTermCreditLimit\": null,\n" +
            "    \"currentLongTermCreditUtilisation\": null,\n" +
            "    \"changeInLongTermDebt\": -327,\n" +
            "    \"numPositiveScoreFactors\": 9,\n" +
            "    \"numNegativeScoreFactors\": 0,\n" +
            "    \"equifaxScoreBand\": 4,\n" +
            "    \"equifaxScoreBandDescription\": \"Excellent\",\n" +
            "    \"daysUntilNextReport\": 9\n" +
            "  },\n" +
            "  \"dashboardStatus\": \"PASS\",\n" +
            "  \"personaType\": \"INEXPERIENCED\",\n" +
            "  \"coachingSummary\": {\n" +
            "    \"activeTodo\": false,\n" +
            "    \"activeChat\": true,\n" +
            "    \"numberOfTodoItems\": 0,\n" +
            "    \"numberOfCompletedTodoItems\": 0,\n" +
            "    \"selected\": true\n" +
            "  },\n" +
            "  \"augmentedCreditScore\": null\n" +
            "}"
}

fun getCreditResponseNoScore(): String {
    return "{\n" +
            "  \"accountIDVStatus\": \"PASS\",\n" +
            "  \"creditReportInfo\": {\n" +
            "    \"scoreBand\": 4,\n" +
            "    \"clientRef\": \"CS-SED-655426-708782\",\n" +
            "    \"status\": \"MATCH\",\n" +
            "    \"maxScoreValue\": 700,\n" +
            "    \"minScoreValue\": 0,\n" +
            "    \"monthsSinceLastDefaulted\": -1,\n" +
            "    \"hasEverDefaulted\": false,\n" +
            "    \"monthsSinceLastDelinquent\": 1,\n" +
            "    \"hasEverBeenDelinquent\": true,\n" +
            "    \"percentageCreditUsed\": 44,\n" +
            "    \"percentageCreditUsedDirectionFlag\": 1,\n" +
            "    \"changedScore\": 0,\n" +
            "    \"currentShortTermDebt\": 13758,\n" +
            "    \"currentShortTermNonPromotionalDebt\": 13758,\n" +
            "    \"currentShortTermCreditLimit\": 30600,\n" +
            "    \"currentShortTermCreditUtilisation\": 44,\n" +
            "    \"changeInShortTermDebt\": 549,\n" +
            "    \"currentLongTermDebt\": 24682,\n" +
            "    \"currentLongTermNonPromotionalDebt\": 24682,\n" +
            "    \"currentLongTermCreditLimit\": null,\n" +
            "    \"currentLongTermCreditUtilisation\": null,\n" +
            "    \"changeInLongTermDebt\": -327,\n" +
            "    \"numPositiveScoreFactors\": 9,\n" +
            "    \"numNegativeScoreFactors\": 0,\n" +
            "    \"equifaxScoreBand\": 4,\n" +
            "    \"equifaxScoreBandDescription\": \"Excellent\",\n" +
            "    \"daysUntilNextReport\": 9\n" +
            "  },\n" +
            "  \"dashboardStatus\": \"PASS\",\n" +
            "  \"personaType\": \"INEXPERIENCED\",\n" +
            "  \"coachingSummary\": {\n" +
            "    \"activeTodo\": false,\n" +
            "    \"activeChat\": true,\n" +
            "    \"numberOfTodoItems\": 0,\n" +
            "    \"numberOfCompletedTodoItems\": 0,\n" +
            "    \"selected\": true\n" +
            "  },\n" +
            "  \"augmentedCreditScore\": null\n" +
            "}"
}

fun getCreditResponseNoMaxScore(): String {
    return "{\n" +
            "  \"accountIDVStatus\": \"PASS\",\n" +
            "  \"creditReportInfo\": {\n" +
            "    \"score\": 327,\n" +
            "    \"scoreBand\": 4,\n" +
            "    \"clientRef\": \"CS-SED-655426-708782\",\n" +
            "    \"status\": \"MATCH\",\n" +
            "    \"minScoreValue\": 0,\n" +
            "    \"monthsSinceLastDefaulted\": -1,\n" +
            "    \"hasEverDefaulted\": false,\n" +
            "    \"monthsSinceLastDelinquent\": 1,\n" +
            "    \"hasEverBeenDelinquent\": true,\n" +
            "    \"percentageCreditUsed\": 44,\n" +
            "    \"percentageCreditUsedDirectionFlag\": 1,\n" +
            "    \"changedScore\": 0,\n" +
            "    \"currentShortTermDebt\": 13758,\n" +
            "    \"currentShortTermNonPromotionalDebt\": 13758,\n" +
            "    \"currentShortTermCreditLimit\": 30600,\n" +
            "    \"currentShortTermCreditUtilisation\": 44,\n" +
            "    \"changeInShortTermDebt\": 549,\n" +
            "    \"currentLongTermDebt\": 24682,\n" +
            "    \"currentLongTermNonPromotionalDebt\": 24682,\n" +
            "    \"currentLongTermCreditLimit\": null,\n" +
            "    \"currentLongTermCreditUtilisation\": null,\n" +
            "    \"changeInLongTermDebt\": -327,\n" +
            "    \"numPositiveScoreFactors\": 9,\n" +
            "    \"numNegativeScoreFactors\": 0,\n" +
            "    \"equifaxScoreBand\": 4,\n" +
            "    \"equifaxScoreBandDescription\": \"Excellent\",\n" +
            "    \"daysUntilNextReport\": 9\n" +
            "  },\n" +
            "  \"dashboardStatus\": \"PASS\",\n" +
            "  \"personaType\": \"INEXPERIENCED\",\n" +
            "  \"coachingSummary\": {\n" +
            "    \"activeTodo\": false,\n" +
            "    \"activeChat\": true,\n" +
            "    \"numberOfTodoItems\": 0,\n" +
            "    \"numberOfCompletedTodoItems\": 0,\n" +
            "    \"selected\": true\n" +
            "  },\n" +
            "  \"augmentedCreditScore\": null\n" +
            "}"
}

fun getCreditResponseNoCreditReportInfo(): String {
    return "{\n" +
            "  \"accountIDVStatus\": \"PASS\",\n" +
            "  \"dashboardStatus\": \"PASS\",\n" +
            "  \"personaType\": \"INEXPERIENCED\",\n" +
            "  \"coachingSummary\": {\n" +
            "    \"activeTodo\": false,\n" +
            "    \"activeChat\": true,\n" +
            "    \"numberOfTodoItems\": 0,\n" +
            "    \"numberOfCompletedTodoItems\": 0,\n" +
            "    \"selected\": true\n" +
            "  },\n" +
            "  \"augmentedCreditScore\": null\n" +
            "}"
}

fun getCreditResponseMalformedScore(): String {
    return "{\n" +
            "  \"accountIDVStatus\": \"PASS\",\n" +
            "  \"creditReportInfo\": {\n" +
            "    \"score\": dfg,\n" +
            "    \"scoreBand\": 4,\n" +
            "    \"clientRef\": \"CS-SED-655426-708782\",\n" +
            "    \"status\": \"MATCH\",\n" +
            "    \"maxScoreValue\": 700,\n" +
            "    \"minScoreValue\": 0,\n" +
            "    \"monthsSinceLastDefaulted\": -1,\n" +
            "    \"hasEverDefaulted\": false,\n" +
            "    \"monthsSinceLastDelinquent\": 1,\n" +
            "    \"hasEverBeenDelinquent\": true,\n" +
            "    \"percentageCreditUsed\": 44,\n" +
            "    \"percentageCreditUsedDirectionFlag\": 1,\n" +
            "    \"changedScore\": 0,\n" +
            "    \"currentShortTermDebt\": 13758,\n" +
            "    \"currentShortTermNonPromotionalDebt\": 13758,\n" +
            "    \"currentShortTermCreditLimit\": 30600,\n" +
            "    \"currentShortTermCreditUtilisation\": 44,\n" +
            "    \"changeInShortTermDebt\": 549,\n" +
            "    \"currentLongTermDebt\": 24682,\n" +
            "    \"currentLongTermNonPromotionalDebt\": 24682,\n" +
            "    \"currentLongTermCreditLimit\": null,\n" +
            "    \"currentLongTermCreditUtilisation\": null,\n" +
            "    \"changeInLongTermDebt\": -327,\n" +
            "    \"numPositiveScoreFactors\": 9,\n" +
            "    \"numNegativeScoreFactors\": 0,\n" +
            "    \"equifaxScoreBand\": 4,\n" +
            "    \"equifaxScoreBandDescription\": \"Excellent\",\n" +
            "    \"daysUntilNextReport\": 9\n" +
            "  },\n" +
            "  \"dashboardStatus\": \"PASS\",\n" +
            "  \"personaType\": \"INEXPERIENCED\",\n" +
            "  \"coachingSummary\": {\n" +
            "    \"activeTodo\": false,\n" +
            "    \"activeChat\": true,\n" +
            "    \"numberOfTodoItems\": 0,\n" +
            "    \"numberOfCompletedTodoItems\": 0,\n" +
            "    \"selected\": true\n" +
            "  },\n" +
            "  \"augmentedCreditScore\": null\n" +
            "}"
}
