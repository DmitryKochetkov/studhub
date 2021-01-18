import React, {Component} from "react";

class SpecificationStatisticsTable extends Component {
    constructor(props) {
        super(props);
        this.state = {
            specification: props.specification,
            specification_statistics: props.specification_statistics
        };
    }

    render() {
        const {specification, specification_statistics} = this.state;

        let progressTable = <div>Отсутствует спецификация.</div>
        if (specification) {
            let problemCodesByIndex = new Map();
            specification.problemCodes.map((data, index) => {
                problemCodesByIndex[data.id] = {
                    specificationIndex: data.numberInSpecification,
                    description: data.description,
                    totalSubmissions: null,
                    correctSubmissions: null
                }
            });

            specification_statistics.statistics.forEach(element => {
                problemCodesByIndex[element.problemCodeId].totalSubmissions = element.totalSubmissions;
                problemCodesByIndex[element.problemCodeId].correctSubmissions = element.correctSubmissions;
            });

            progressTable = <SpecificationStatisticsTable specification={specification}
                                                          specification_statistics={specification_statistics}/>


            return <table className={"specification-statistics-table"}>
                <tbody>
                <tr>
                    {specification.problemCodes.map((data, index) => {
                        let problemStatistics = problemCodesByIndex[data.id];
                        let statisticsInfo = data.description;

                        const {correctSubmissions, totalSubmissions} = problemStatistics;
                        const percentage = (correctSubmissions / totalSubmissions) * 100;

                        var r, g, b = 0;
                        if (percentage < 50) {
                            r = 255;
                            g = Math.round(5.1 * percentage);
                        } else {
                            g = 255;
                            r = Math.round(510 - 5.10 * percentage);
                        }
                        var h = r * 0x10000 + g * 0x100 + b * 0x1;

                        if (problemStatistics.totalSubmissions)
                            statisticsInfo += "\nУспешных попыток: " + problemStatistics.correctSubmissions + "/" + problemStatistics.totalSubmissions;
                        else statisticsInfo += "\nНет посылок задач этого типа."
                        return (<td style={{backgroundColor: '#' + ('000000' + h.toString(16)).slice(-6)}}><span
                            title={statisticsInfo}>{data.numberInSpecification}</span></td>);
                    })}
                </tr>
                </tbody>
            </table>;
        }
        else return "Отсутствует спецификация";
    }
}

export default SpecificationStatisticsTable;