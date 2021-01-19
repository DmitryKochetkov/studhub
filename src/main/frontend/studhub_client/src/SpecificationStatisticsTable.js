import React, {Component} from "react";

class SpecificationStatisticsTable extends Component {
    constructor(props) {
        super(props);
        this.state = {
            specification: props.specification,
            specificationStatistics: props.specificationStatistics
        };
    }

    render() {
        const {specification, specificationStatistics} = this.state;

        if (specification && specificationStatistics) {
            let problemCodesByIndex = new Map();
            specification.problemCodes.map((data, index) => {
                problemCodesByIndex[data.id] = {
                    specificationIndex: data.indexInSpecification,
                    description: data.description,
                    totalSubmissions: null,
                    correctSubmissions: null
                }
            });

            specificationStatistics.data.forEach(element => {
                problemCodesByIndex[element.problemCodeId].totalSubmissions = element.totalSubmissions;
                problemCodesByIndex[element.problemCodeId].correctSubmissions = element.correctSubmissions;
            });

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
                            statisticsInfo += "\nУспешных попыток: " + correctSubmissions + "/" + totalSubmissions + " (" + percentage +"%)";
                        else statisticsInfo += "\nНет посылок задач этого типа."
                        return (<td style={{backgroundColor: '#' + ('000000' + h.toString(16)).slice(-6)}}><span
                            title={statisticsInfo}>{data.indexInSpecification}</span></td>);
                    })}
                </tr>
                </tbody>
            </table>;
        }
        else return "Отсутствует спецификация";
    }
}

export default SpecificationStatisticsTable;