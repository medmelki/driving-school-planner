app.directive('calendar', ['AppointmentService',
    function (AppointmentService) {
        return {
            restrict: 'AE',
            scope: true,
            controller: function ($scope, $element, $attrs) {
                $scope.fullCalendar = function (e, ui) {
                    $scope.model = ui.value;
                    // or set it on the model
                    // DataModel.model = ui.value;
                    // add to angular digest cycle
                    $scope.$digest();
                };
                this.findAllAppointments = function () {
                    AppointmentService.findAllAppointments()
                        .then(
                            function (d) {
                                console.log("test ", d);
                                self.appointments = d;
                            },
                            function (errResponse) {
                                console.error('Error while fetching Appointments');
                            }
                        );
                };
                this.findAllAppointments();
            },
            link: function (scope, el, attrs) {

                var options = {
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
                        var title = prompt('Event Title:');
                        var eventData;
                        if (title) {
                            eventData = {
                                title: title,
                                start: start,
                                end: end
                            };
                            $('#calendar').fullCalendar('renderEvent', eventData, true); // stick? = true
                        }
                        $('#calendar').fullCalendar('unselect');
                    },
                    editable: true,
                    eventLimit: true, // allow "more" link when too many events
                    events: [
                        {
                            title: 'All Day Event',
                            start: '2016-12-01'
                        },
                        {
                            title: 'Long Event',
                            start: '2016-12-07',
                            end: '2016-12-10'
                        },
                        {
                            id: 999,
                            title: 'Repeating Event',
                            start: '2016-12-09T16:00:00'
                        },
                        {
                            id: 999,
                            title: 'Repeating Event',
                            start: '2016-12-16T16:00:00'
                        },
                        {
                            title: 'Conference',
                            start: '2016-12-11',
                            end: '2016-12-13'
                        },
                        {
                            title: 'Meeting',
                            start: '2016-12-12T10:30:00',
                            end: '2016-12-12T12:30:00'
                        },
                        {
                            title: 'Lunch',
                            start: '2016-12-12T12:00:00'
                        },
                        {
                            title: 'Meeting',
                            start: '2016-12-12T14:30:00'
                        },
                        {
                            title: 'Happy Hour',
                            start: '2016-12-12T17:30:00'
                        },
                        {
                            title: 'Dinner',
                            start: '2016-12-12T20:00:00'
                        },
                        {
                            title: 'Birthday Party',
                            start: '2016-12-13T07:00:00'
                        },
                        {
                            title: 'Click for Google',
                            url: 'http://google.com/',
                            start: '2016-12-28'
                        }
                    ]
                };

                // set up calendar on load
                angular.element(document).ready(function () {
                    scope.$calendar = $(el).fullCalendar(options);
                });
            }
        }
    }
]);