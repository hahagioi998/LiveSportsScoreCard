<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <link href="styles.css" rel="stylesheet"/>
</head>
<body>
<h1>Home</h1>
<table>

    <tbody>
    <tr>
        <td>
            Event Status
        </td>

        <c:if test="${eventStatus}">
            <td class="event-emission-not-running">
                Running
            </td>
        </c:if>
        <c:if test="${not eventStatus}">
            <td class="event-emission-running">
                Not Running
            </td>
        </c:if>
        <c:if test="${eventStatus}">
            <td>
                <form action="/stop-event-generation" method="post">
                    <button type="submit">Stop</button>
                </form>
            </td>
        </c:if>
        <c:if test="${not eventStatus}">
            <td>
                <form action="/start-event-generation" method="post">
                    <button type="submit">Start</button>
                </form>
            </td>
        </c:if>
    </tr>
    </tbody>
</table>
<br>

<c:if test="${eventStatus}">
    <table>
        <thead>
        <tr>
            <th style="background-color: greenyellow" colspan="4">Sport Status</th>
        </tr>
        <tr>
            <th>#</th>
            <th>Sport</th>
            <th>Status</th>
            <th>Pause / Pause Data Generation</th>
        </tr>
        </thead>
        <tbody>
        <c:if test="${soccerConfigured}">
            <tr>
                <td>1</td>
                <td>Soccer</td>
                <c:if test="${soccerStatus}">
                    <td class="event-emission-not-running">
                        Running
                    </td>
                </c:if>
                <c:if test="${not soccerStatus}">
                    <td class="event-emission-running">
                        Not Running
                    </td>
                </c:if>
                <c:if test="${soccerStatus}">
                    <td>
                        <form action="/pause-event-generation" method="post">
                            <input type="hidden" name="sport" value="soccer"/>
                            <button type="submit">Pause</button>
                        </form>
                    </td>
                </c:if>
                <c:if test="${not soccerStatus}">
                    <td>
                        <form action="/unpause-event-generation" method="post">
                            <input type="hidden" name="sport" value="soccer"/>
                            <button type="submit">Unpause</button>
                        </form>
                    </td>
                </c:if>
            </tr>
        </c:if>

        <c:if test="${hockeyConfigured}">
            <tr>
                <td>2</td>
                <td>Hockey</td>
                <c:if test="${hockeyStatus}">
                    <td class="event-emission-not-running">
                        Running
                    </td>
                </c:if>
                <c:if test="${not hockeyStatus}">
                    <td class="event-emission-running">
                        Not Running
                    </td>
                </c:if>
                <c:if test="${hockeyStatus}">
                    <td>
                        <form action="/pause-event-generation" method="post">
                            <input type="hidden" name="sport" value="hockey"/>
                            <button type="submit">Pause</button>
                        </form>
                    </td>
                </c:if>
                <c:if test="${not hockeyStatus}">
                    <td>
                        <form action="/unpause-event-generation" method="post">
                            <input type="hidden" name="sport" value="hockey"/>
                            <button type="submit">Unpause</button>
                        </form>
                    </td>
                </c:if>
            </tr>
        </c:if>

        <c:if test="${basketballConfigured}">
            <tr>
                <td>3</td>
                <td>Basketball</td>
                <c:if test="${basketballStatus}">
                    <td class="event-emission-not-running">
                        Running
                    </td>
                </c:if>
                <c:if test="${not basketballStatus}">
                    <td class="event-emission-running">
                        Not Running
                    </td>
                </c:if>
                <c:if test="${basketballStatus}">
                    <td>
                        <form action="/pause-event-generation" method="post">
                            <input type="hidden" name="sport" value="basketball"/>
                            <button type="submit">Pause</button>
                        </form>
                    </td>
                </c:if>
                <c:if test="${not basketballStatus}">
                    <td>
                        <form action="/unpause-event-generation" method="post">
                            <input type="hidden" name="sport" value="basketball"/>
                            <button type="submit">Unpause</button>
                        </form>
                    </td>
                </c:if>
            </tr>
        </c:if>
        </tbody>
    </table>
</c:if>

<br>

<form action="/publish-event" method="post">

    <select name="queue" id="queue">
        <option value="soccer">Soccer</option>
        <option value="hockey">Hockey</option>
        <option value="basketball">Basketball</option>
    </select>
    <input type="text" name="information"/>
    <button type="submit">Publish</button>
</form>
</body>
</html>