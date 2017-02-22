app.directive('calendar', ['AppointmentService', '$filter',
    function (AppointmentService, $filter) {
        return {
            restrict: 'AE',
            scope: true,

            link: function (scope, el, attrs) {

                scope.fullCalendar = function (e, ui) {
                    $scope.model = ui.value;
                    // or set it on the model
                    // DataModel.model = ui.value;
                    // add to angular digest cycle
                    $scope.$digest();
                };

                scope.options = {
                    header: {
                        left: 'prev,next today',
                        center: 'title',
                        right: 'month,agendaWeek,agendaDay'
                    },
                    defaultDate: new Date().toJSON().slice(0, 10),
                    locale: 'fr',
                    navLinks: true, // can click day/week names to navigate views
                    selectable: true,
                    selectHelper: true,
                    select: function (start, end) {
                        $('#addAppointmentModal').modal();
                        // var title = prompt('Event Title:');
                        var title = '';
                        var eventData;
                        scope.$parent.appCtrl.appointment.start = moment(start._d).toDate().getTime();
                        scope.$parent.appCtrl.appointment.end = moment(end._d).toDate().getTime();
                        if (title) {
                            eventData = {
                                title: title,
                                start: start,
                                end: end
                            };
                        }
                        $('#calendar').fullCalendar('renderEvent', eventData, true); // stick? = true
                        $('#calendar').fullCalendar('unselect');
                    },
                    eventClick: function (event, jsEvent, view) {
                        var $contextMenu = $("#contextMenu");
                        $contextMenu.css({
                            display: "block",
                            left: jsEvent.pageX,
                            top: jsEvent.pageY
                        });
                        $('#context-info-btn').click(function () {
                            showEventInfos(event);
                            $contextMenu.hide();
                        });
                        $('#context-modify-btn').click(function () {
                            showEventInfos(event);
                            $contextMenu.hide();
                        });
                        $('#context-delete-btn').click(function () {
                            // TODO: modifyEvent Modal(Add event modal with edit mode)
                            showEventInfos(event);
                            $contextMenu.hide();
                        });
                        $('#context-close-btn').click(function () {
                            $contextMenu.hide();
                        });
                    },
                    editable: true,
                    eventLimit: true, // allow "more" link when too many events
                    // events: [
                    // {
                    //     title: 'All Day Event',
                    //     start: '2016-12-01'
                    // },
                    // {
                    //     title: 'Long Event',
                    //     start: '2016-12-07',
                    //     end: '2016-12-10'
                    // },
                    // {
                    //     id: 999,
                    //     title: 'Repeating Event',
                    //     start: '2016-12-09T16:00:00'
                    // },
                    // {
                    //     id: 999,
                    //     title: 'Repeating Event',
                    //     start: '2016-12-16T16:00:00'
                    // },
                    // {
                    //     title: 'Conference',
                    //     start: '2016-12-11',
                    //     end: '2016-12-13'
                    // },
                    // {
                    //     title: 'Meeting',
                    //     start: '2016-12-12T10:30:00',
                    //     end: '2016-12-12T12:30:00'
                    // },
                    // {
                    //     title: 'Lunch',
                    //     start: '2016-12-12T12:00:00'
                    // },
                    // {
                    //     title: 'Meeting',
                    //     start: '2016-12-12T14:30:00'
                    // },
                    // {
                    //     title: 'Happy Hour',
                    //     start: '2016-12-12T17:30:00'
                    // },
                    // {
                    //     title: 'Dinner',
                    //     start: '2016-12-12T20:00:00'
                    // },
                    // {
                    //     title: 'Birthday Party',
                    //     start: '2016-12-13T07:00:00'
                    // },
                    //     {
                    //         title: 'Click for Google',
                    //         url: 'http://google.com/',
                    //         start: '2016-12-28'
                    //     }
                    // ]
                    events: []
                };

                scope.findAllAppointments = function () {
                    AppointmentService.findAllAppointments()
                        .then(
                            function (d) {
                                for (var i = 0; i < d.length; i++) {
                                    var appointment = d[i];
                                    appointment.title = d[i].card.firstname + " " + d[i].card.lastname;
                                    appointment.start = $filter('date')(d[i].start, "yyyy-MM-dd HH:mm:ss Z");
                                    appointment.end = $filter('date')(d[i].end, "yyyy-MM-dd HH:mm:ss Z");
                                    scope.options.events.push(appointment);
                                }

                                scope.$calendar = $(el).fullCalendar(scope.options);

                            },
                            function (error) {
                                console.error('Error while fetching Appointments' + error);
                            }
                        );
                };

                // set up calendar on load
                angular.element(document).ready(function () {
                    scope.findAllAppointments();
                });
            }
        }
    }
]);

function showEventInfos(event) {
    $('.modal-title:first').html(event.title);
    var modalBody = $('.modal-body:first');
    modalBody.html('<strong>Contrat n° : </strong>' + event.card.contractId + '<br>');

    modalBody.append('<strong>Email : </strong>' +
        (event.card.email ? event.card.email : '_') + '<br>');
    modalBody.append('<strong>Type : </strong>' +
        (event.card.type ? event.card.type : '_') + '<br>');
    modalBody.append('<strong>Telephone : </strong>' +
        (event.card.telephone ? event.card.telephone : '_') + '<br>');
    $('#showAppointmentModal').modal();
}