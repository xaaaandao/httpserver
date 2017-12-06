package functions;

import java.io.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * Descrição: essa classe é onde geramos mesmo os arquivos HTML dependendo do
 * path vindo do cliente, aqui geramos os arquivos do virtual path (que no nosso
 * caso é admin) e no arquivo que geramos e mostramos no conteúdo do diretório.
 * Autor: Alexandre Yuji Kajihara
 */
public class FileHtml {

    static String startDateHour;
    static List<InfoRequest> filesRequired;
    static long start;

    /**
     * O método headerDirectoryHtml(String directory, String sortFiles) abre as
     * TAG que foram abertas para que pudessemos listar o conteúdo do diretório
     * solicitado pelo cliente.
     *
     * @param directory String com o nome do diretório solicitado pelo cliente.
     * @param sortFiles String que será ordenado os elementos dos arquivos.
     * @return String com o conteúdo do arquivo HTML, abrindo as TAG que
     * conterão os diretórios listados.
     */
    public String headerDirectoryHtml(String directory, String sortFiles) {
        String head = "<!DOCTYPE html>\n"
                + "<link href='https://fonts.googleapis.com/css?family=Annie Use Your Telescope' rel='stylesheet'>\n"
                + "<html>\n"
                + "<head>\n"
                + "<style>\n"
                + "body { font-family: 'Annie Use Your Telescope'; font-size: 20px; }"
                + "table{ width: 50%; }\n"
                + "td, td{ text-align: center; padding: 8px; }\n"
                + "</style>\n"
                + "<title> Index of " + directory + " </title>\n"
                + "</head>\n"
                + "<body>\n"
                + "<h1> Index of " + directory + "</h1>\n"
                + "<table>\n"
                + "<tbody>\n"
                + "<tr>\n";

        String name = " ";
        directory = directory.replace("/html", "");
        if (directory.length() > 1 && directory.charAt(directory.length() - 1) == '/') {
            directory = directory.substring(0, directory.length() - 1);
        }
        if (sortFiles.equalsIgnoreCase("sortN")) {
            name = "<th> <a href='" + directory + "/?n=r' style=\"text-decoration: none; color : #000000;\"> Name </a> </th>\n";
        } else {
            name = "<th> <a href='" + directory + "/?n=o' style=\"text-decoration: none; color : #000000;\"> Name </a> </th>\n";
        }

        String lastModified = " ";
        if (sortFiles.equalsIgnoreCase("sortL")) {
            lastModified = "<th> <a href='" + directory + "/?l=r' style=\"text-decoration: none; color : #000000;\"> Last modified </a> </th>\n";
        } else {
            lastModified = "<th> <a href='" + directory + "/?l=o' style=\"text-decoration: none; color : #000000;\"> Last modified </a> </th>\n";
        }

        String size = " ";
        if (sortFiles.equalsIgnoreCase("sortS")) {
            size = "<th> <a href='" + directory + "/?s=r' style=\"text-decoration: none; color : #000000;\"> Size </a> </th>\n";
        } else {
            size = "<th> <a href='" + directory + "/?s=o' style=\"text-decoration: none; color : #000000;\"> Size </a> </th>\n";
        }

        String footer = "</tr>\n <hr>\n";
        return head + name + lastModified + size + footer;
    }

    /**
     * O método footerDirectoryHtml() fecha as TAG que foram abertas no método
     * headerDirectoryHtml() que compõe o arquivo HTML que irá mostrar os
     * arquivos e diretórios presentes no diretório solicitado.
     *
     * @return String com o conteúdo do arquivo HTML, fechando as TAG que foram
     * aberta.
     */
    public String footerDirectoryHtml() {
        return "</tbody>\n" + "</table>\n" + "</body>\n" + "</html>\n" + "</>\n";
    }

    /**
     * O método filesHtml(String directory, List<Arquivo> listFiles) é invocado
     * quando o usuário solicita um diretório, então esse método gera um parte
     * do HTML que exibe os arquivos presente naquele diretório.
     *
     * @param directory String com o nome do diretório solicitado pelo cliente.
     * @param listFiles Lista com o nome de todos os arquivos presente naquele
     * diretório.
     * @return content String com o conteúdo do arquivo HTML, mostrando tudo que
     * está presente no diretório.
     */
    public String filesHtml(String directory, List<Arquivo> listFiles) {
        String content = new String();
        /* For each: você quer iterar, mas sem uma ordem específica */
        /* For: quando você sabe o tamanho */
        /* While: quando você não sabe o tamanho */
        /* Se o diretório for barra não tem diretório pai caso contrário irá aparecer na página */
        if (!directory.equalsIgnoreCase("/html")) {
            String parentDirectory = new ListDirectory().getParentDirectory(directory);
            content = "<tr>\n" + "<td> <a href='" + parentDirectory + "'\"> parent directory </a> </td>";
            content = content + "<td> - </td>";
            content = content + "<td> - </td>\n</tr>\n";
        }

        /* Imprime os arquivos e diretórios no arquivo HTML presentes naquele determinado diretório */
        for (Arquivo a : listFiles) {
            if (!a.getName().equalsIgnoreCase("directory.html") && !a.getName().contains("error40") && !a.getName().equalsIgnoreCase("config.xml") && !a.getName().equalsIgnoreCase("admin.html") && !a.getName().equalsIgnoreCase("directorySortReverseLastModified.html") && !a.getName().equalsIgnoreCase("directorySortLastModified.html") && !a.getName().equalsIgnoreCase("directorySortReverseSize.html") && !a.getName().equalsIgnoreCase("directorySortReverseName.html") && !a.getName().equalsIgnoreCase("directorySortName.html") && !a.getName().equalsIgnoreCase("directorySortSize.html") && !a.getName().equalsIgnoreCase("infoAdmin.html")) {
                String redirect = directory + '/' + a.getName();
                if (redirect.contains("/html")) {
                    redirect = redirect.replace("/html", "");
                }
                content = content + "<tr>\n" + "<td> <a href='" + redirect + "'>" + a.getName() + "</a> </td>\n";
                content = content + "<td> <a>" + a.getLastModified() + "</a> </td>\n";
                content = content + "<td> <a>" + a.getSize() + "</a> Mb </td>\n" + "</tr>\n";
            }
        }
        return content;
    }

    /**
     * O método getTimeAndDate() pega a horário e a data atual que o servidor
     * começou a executar, além disso instância a lista de arquivos requiridos,
     * lista que contém todos os arquivos requisitados pelos clientes e também
     * inicializa o tempo que é útil para calcular os minutos e segundos que o
     * servidor que está sendo executado.
     *
     * @param void, ou seja, não retorna nada.
     */
    public void getTimeAndDate() {
        /* start útil para pegar os minutos e segundos que o servidor está executando */
        start = System.nanoTime();
        startDateHour = new SimpleDateFormat("yyyy/MM/d HH:mm:ss", Locale.ENGLISH).format(new Date());

        /* Instanciando a lista de arquivos requiridos */
        filesRequired = new ArrayList<InfoRequest>();
    }

    /**
     * O método executionTime() pega quantos milisegundos o servidor está
     * ligado, e transforma esses valores em minutos e segundos e retorna no
     * formato de uma String.
     *
     * @return String com os minutos e os segundos que o servidor está ligado.
     */
    public String executionTime() {
        /* Retornando minutos e segundos que o servidor está ligado */
        long end = System.nanoTime();
        long time = ((end - start) / 1000000);
        return String.format("%d minutos %d segundos", TimeUnit.MILLISECONDS.toMinutes(time), TimeUnit.MILLISECONDS.toSeconds(time) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time)));
    }

    /**
     * O método generateInfoAdmin() gera um arquivo HTML que contém as seguintes
     * informações a hora e data que ele está ligado, a quantidade minutos e
     * segundos em que ele está ligado, as últimas requisições solicitadas pelo
     * cliente, quando for solicitado um diretório mostra também o diretório que
     * foi solicitado.
     *
     * @param void, ou seja, não retorna nada.
     */
    public void generateInfoAdmin() {
        String content = "<p> O servidor está no ar desde: " + startDateHour + " (" + executionTime() + " ligado).</p>\n"
                + "<p> Número de requisições atendidas: " + Integer.toString(filesRequired.size()) + "</p>"
                + "<p> Últimas requisições:  </p> \n"
                + "<ul>\n";

        for (int i = filesRequired.size() - 1; i >= 0; i--) {
            String location = filesRequired.get(i).getPage();
            location = location.replace("/html", "");
            if (filesRequired.get(i).getNameDirectory().equalsIgnoreCase("null")) {
                content = content + "<li> <a href=\"" + location + "\">" + filesRequired.get(i).getPage() + "</a> </li>\n";
            } else {
                content = content + "<li> <a href=\"" + location + "\">" + filesRequired.get(i).getPage() + "</a>  <ul> <li>" + filesRequired.get(i).getNameDirectory() + "</li> </ul> </li>\n";
            }
        }

        content = content + "</ul>";

        try (BufferedWriter f = new BufferedWriter(new FileWriter("/html/infoAdmin.html"))) {
            /* Preenchendo o arquivo HTML do virtual path */
            f.write(content);
            f.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * O método generateadminHtml() gera o arquivo HTML, que irá conter as informações
     * de outro arquivo, e mais o slider feito em angular JS.
     *
     * @return content String com o conteúdo presente no arquivo HTML.
     */
    public String generateAdminHtml() {
        String content = "<!DOCTYPE html PUBLIC \"-//IETF//DTD HTML 2.0//EN\">\n"
                + "<html ng-app=\"sliderDemoApp\" id=\"top\">\n"
                + "    <head>\n"
                + "        <meta charset=\"utf-8\">\n"
                + "        <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\">\n"
                + "        <link rel=\"stylesheet\" href=\"https://netdna.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.css\">\n"
                + "        <link rel=\"stylesheet\" href=\"https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css\">\n"
                + "        <link href='https://fonts.googleapis.com/css?family=Annie Use Your Telescope' rel='stylesheet'>\n"
                + "        <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\">\n"
                + "        <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\"></script>\n"
                + "        <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js\"></script>\n"
                + "        <script src=\"https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js\"></script>\n"
                + "        <script src=\"https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.js\"></script>\n"
                + "        <title> Information Web Server </title>\n"
                + "        <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.3.0/jquery.min.js\"></script>\n"
                + "        <script>\n"
                + "            angular.module('ui.slider', []).value('uiSliderConfig',{}).directive('uiSlider', ['uiSliderConfig', '$timeout', function(uiSliderConfig, $timeout) {\n"
                + "                uiSliderConfig = uiSliderConfig || {};\n"
                + "                return {\n"
                + "                    require: 'ngModel',\n"
                + "                    compile: function() {\n"
                + "                        var preLink = function (scope, elm, attrs, ngModel) {\n"
                + "\n"
                + "                            function parseNumber(n, decimals) {\n"
                + "                                return (decimals) ? parseFloat(n) : parseInt(n, 10);\n"
                + "                            }\n"
                + "\n"
                + "                            var directiveOptions = angular.copy(scope.$eval(attrs.uiSlider));\n"
                + "                            var options = angular.extend(directiveOptions || {}, uiSliderConfig);\n"
                + "                            // Object holding range values\n"
                + "                            var prevRangeValues = {\n"
                + "                                min: null,\n"
                + "                                max: null\n"
                + "                            };\n"
                + "\n"
                + "                            // convenience properties\n"
                + "                            var properties = ['min', 'max', 'step', 'lowerBound', 'upperBound'];\n"
                + "                            var useDecimals = (!angular.isUndefined(attrs.useDecimals)) ? true : false;\n"
                + "                            var updateOn = (angular.isDefined(options['updateOn'])) ? options['updateOn'] : 'slide'\n"
                + "\n"
                + "                            var init = function() {\n"
                + "                                // When ngModel is assigned an array of values then range is expected to be true.\n"
                + "                                // Warn user and change range to true else an error occurs when trying to drag handle\n"
                + "                                if (angular.isArray(ngModel.$viewValue) && options.range !== true) {\n"
                + "                                    console.warn('Change your range option of ui-slider. When assigning ngModel an array of values then the range option should be set to true.');\n"
                + "                                    options.range = true;\n"
                + "                                }\n"
                + "\n"
                + "                                // Ensure the convenience properties are passed as options if they're defined\n"
                + "                                // This avoids init ordering issues where the slider's initial state (eg handle\n"
                + "                                // position) is calculated using widget defaults\n"
                + "                                // Note the properties take precedence over any duplicates in options\n"
                + "                                angular.forEach(properties, function(property) {\n"
                + "                                    if (angular.isDefined(attrs[property])) {\n"
                + "                                        options[property] = parseNumber(attrs[property], useDecimals);\n"
                + "                                    }\n"
                + "                                });\n"
                + "\n"
                + "                                elm.slider(options);\n"
                + "                                init = angular.noop;\n"
                + "                            };\n"
                + "\n"
                + "                            // Find out if decimals are to be used for slider\n"
                + "                            angular.forEach(properties, function(property) {\n"
                + "                                // support {{}} and watch for updates\n"
                + "                                attrs.$observe(property, function(newVal) {\n"
                + "                                    if (!!newVal) {\n"
                + "                                        init();\n"
                + "                                        options[property] = parseNumber(newVal, useDecimals);\n"
                + "                                        elm.slider('option', property, parseNumber(newVal, useDecimals));\n"
                + "                                        ngModel.$render();\n"
                + "                                    }\n"
                + "                                });\n"
                + "                            });\n"
                + "                            attrs.$observe('disabled', function(newVal) {\n"
                + "                                init();\n"
                + "                                elm.slider('option', 'disabled', !!newVal);\n"
                + "                            });\n"
                + "\n"
                + "                            // Watch ui-slider (byVal) for changes and update\n"
                + "                            scope.$watch(attrs.uiSlider, function(newVal) {\n"
                + "                                init();\n"
                + "                                if(newVal !== undefined) {\n"
                + "                                  elm.slider('option', newVal);\n"
                + "                                }\n"
                + "                            }, true);\n"
                + "\n"
                + "                            // Late-bind to prevent compiler clobbering\n"
                + "                            $timeout(init, 0, true);\n"
                + "\n"
                + "                            // Update model value from slider\n"
                + "                            elm.bind(updateOn, function(event, ui) {\n"
                + "                                var valuesChanged;\n"
                + "\n"
                + "                                if (ui.values) {\n"
                + "                                    var boundedValues = ui.values.slice();\n"
                + "\n"
                + "                                    if (options.lowerBound && boundedValues[0] < options.lowerBound) {\n"
                + "                                        boundedValues[0] = Math.max(boundedValues[0], options.lowerBound);\n"
                + "                                    }\n"
                + "                                    if (options.upperBound && boundedValues[1] > options.upperBound) {\n"
                + "                                        boundedValues[1] = Math.min(boundedValues[1], options.upperBound);\n"
                + "                                    }\n"
                + "\n"
                + "                                    if (boundedValues[0] !== ui.values[0] || boundedValues[1] !== ui.values[1]) {\n"
                + "                                        valuesChanged = true;\n"
                + "                                        ui.values = boundedValues;\n"
                + "                                    }\n"
                + "                                } else {\n"
                + "                                    var boundedValue = ui.value;\n"
                + "\n"
                + "                                    if (options.lowerBound && boundedValue < options.lowerBound) {\n"
                + "                                        boundedValue = Math.max(boundedValue, options.lowerBound);\n"
                + "                                    }\n"
                + "                                    if (options.upperBound && boundedValue > options.upperBound) {\n"
                + "                                        boundedValue = Math.min(boundedValue, options.upperBound);\n"
                + "                                    }\n"
                + "\n"
                + "                                    if (boundedValue !== ui.value) {\n"
                + "                                        valuesChanged = true;\n"
                + "                                        ui.value = boundedValue;\n"
                + "                                    }\n"
                + "                                }\n"
                + "\n"
                + "\n"
                + "                                ngModel.$setViewValue(ui.values || ui.value);\n"
                + "                                $(ui.handle).find('.ui-slider-tip').text(ui.value);\n"
                + "                                scope.$apply();\n"
                + "\n"
                + "                                if (valuesChanged) {\n"
                + "                                    setTimeout(function() {\n"
                + "                                        elm.slider('value', ui.values || ui.value);\n"
                + "                                    }, 0);\n"
                + "\n"
                + "                                    return false;\n"
                + "                                }\n"
                + "                            });\n"
                + "\n"
                + "                            // Update slider from model value\n"
                + "                            ngModel.$render = function() {\n"
                + "                                init();\n"
                + "                                var method = options.range === true ? 'values' : 'value';\n"
                + "\n"
                + "                                if (options.range !== true && isNaN(ngModel.$viewValue) && !(ngModel.$viewValue instanceof Array)) {\n"
                + "                                    ngModel.$viewValue = 0;\n"
                + "                                }\n"
                + "                                else if (options.range && !angular.isDefined(ngModel.$viewValue)) {\n"
                + "                                        ngModel.$viewValue = [0,0];\n"
                + "                                }\n"
                + "\n"
                + "                                // Do some sanity check of range values\n"
                + "                                if (options.range === true) {\n"
                + "                                    // previously, the model was a string b/c it was in a text input, need to convert to a array.\n"
                + "                                    // make sure input exists, comma exists once, and it is a string.\n"
                + "                                    if (ngModel.$viewValue && angular.isString(ngModel.$viewValue) && (ngModel.$viewValue.match(/,/g) || []).length === 1) {\n"
                + "                                        // transform string model into array.\n"
                + "                                        var valueArr = ngModel.$viewValue.split(',');\n"
                + "                                        ngModel.$viewValue = [Number(valueArr[0]), Number(valueArr[1])];\n"
                + "                                    }\n"
                + "                                    // Check outer bounds for min and max values\n"
                + "                                    if (angular.isDefined(options.min) && options.min > ngModel.$viewValue[0]) {\n"
                + "                                        ngModel.$viewValue[0] = options.min;\n"
                + "                                    }\n"
                + "                                    if (angular.isDefined(options.max) && options.max < ngModel.$viewValue[1]) {\n"
                + "                                        ngModel.$viewValue[1] = options.max;\n"
                + "                                    }\n"
                + "\n"
                + "                                    // Check min and max range values\n"
                + "                                    if (ngModel.$viewValue[0] > ngModel.$viewValue[1]) {\n"
                + "                                        // Min value should be less to equal to max value\n"
                + "                                        if (prevRangeValues.min >= ngModel.$viewValue[1]) {\n"
                + "                                            ngModel.$viewValue[1] = prevRangeValues.min;\n"
                + "                                        }\n"
                + "                                        // Max value should be less to equal to min value\n"
                + "                                        if (prevRangeValues.max <= ngModel.$viewValue[0]) {\n"
                + "                                            ngModel.$viewValue[0] = prevRangeValues.max;\n"
                + "                                        }\n"
                + "                                    }\n"
                + "\n"
                + "                                    // Store values for later user\n"
                + "                                    prevRangeValues.min = ngModel.$viewValue[0];\n"
                + "                                    prevRangeValues.max = ngModel.$viewValue[1];\n"
                + "\n"
                + "                                }\n"
                + "                                elm.slider(method, ngModel.$viewValue);\n"
                + "                            };\n"
                + "\n"
                + "                            scope.$watch(attrs.ngModel, function() {\n"
                + "                                if (options.range === true) {\n"
                + "                                    ngModel.$render();\n"
                + "\n"
                + "                                    $(elm).find('.ui-slider-tip').each(function(i, tipElm) {\n"
                + "                                        $(tipElm).text(ngModel.$viewValue[i]);\n"
                + "                                    });\n"
                + "                                } else {\n"
                + "                                    $(elm).find('.ui-slider-tip').text(ngModel.$viewValue);\n"
                + "                                }\n"
                + "                            }, true);\n"
                + "\n"
                + "                            function destroy() {\n"
                + "                                if (elm.hasClass('ui-slider')) {\n"
                + "                                    elm.slider('destroy');\n"
                + "                                }\n"
                + "                            }\n"
                + "\n"
                + "                            scope.$on(\"$destroy\", destroy);\n"
                + "                            elm.one('$destroy', destroy);\n"
                + "                        };\n"
                + "\n"
                + "                        var postLink = function (scope, element, attrs, ngModel) {\n"
                + "                            // Add tick marks if 'tick' and 'step' attributes have been setted on element.\n"
                + "                            // Support horizontal slider bar so far. 'tick' and 'step' attributes are required.\n"
                + "                            var options = angular.extend({}, scope.$eval(attrs.uiSlider));\n"
                + "                            var properties = ['min', 'max', 'step', 'tick', 'tip'];\n"
                + "                            angular.forEach(properties, function(property) {\n"
                + "                                if (angular.isDefined(attrs[property])) {\n"
                + "                                    options[property] = attrs[property];\n"
                + "                                }\n"
                + "                            });\n"
                + "                            if (angular.isDefined(options['tick']) && angular.isDefined(options['step'])) {\n"
                + "                                var total = parseInt( (parseInt(options['max']) - parseInt(options['min'])) /parseInt(options['step']));\n"
                + "                                for (var i = total; i >= 0; i--) {\n"
                + "                                    var left = ((i / total) * 100) + '%';\n"
                + "                                    $(\"<div/>\").addClass(\"ui-slider-tick\").appendTo(element).css({left: left});\n"
                + "                                };\n"
                + "                            }\n"
                + "                            if(angular.isDefined(options['tip'])) {\n"
                + "                                $timeout(function(){\n"
                + "                                    var handles = element.find('.ui-slider-handle');\n"
                + "                                    if(handles && handles.length>1 && ngModel.$viewValue && angular.isArray(ngModel.$viewValue)){\n"
                + "                                        $(handles[0]).append('<div class=\"ui-slider-tip\">'+ngModel.$viewValue[0]+'</div>');\n"
                + "                                        $(handles[1]).append('<div class=\"ui-slider-tip\">'+ngModel.$viewValue[1]+'</div>');\n"
                + "                                    }else{\n"
                + "                                        element.find('.ui-slider-handle').append('<div class=\"ui-slider-tip\">'+ngModel.$viewValue+'</div>');\n"
                + "                                    }\n"
                + "                                },10);\n"
                + "                            }\n"
                + "                        }\n"
                + "\n"
                + "                        return {\n"
                + "                            pre: preLink,\n"
                + "                            post: postLink\n"
                + "                        };\n"
                + "                    }\n"
                + "                };\n"
                + "            }]);\n"
                + "            \n"
                + "            var app = angular.module('sliderDemoApp', ['ui.slider']);\n"
                + "                app.factory('colorpicker', function() {\n"
                + "                function hexFromRGB(r, g, b) {\n"
                + "                    var hex = [r.toString(16), g.toString(16), b.toString(16)];\n"
                + "                    angular.forEach(hex, function(value, key) {\n"
                + "                        if (value.length === 1)\n"
                + "                            hex[key] = \"0\" + value;\n"
                + "                    });\n"
                + "                    return hex.join('').toUpperCase();\n"
                + "                }\n"
                + "                return {\n"
                + "                    refreshSwatch: function(r, g, b) {\n"
                + "                        var color = '#' + hexFromRGB(r, g, b);\n"
                + "                        angular.element('#swatch').css('background-color', color);\n"
                + "                    }\n"
                + "                };\n"
                + "            });\n"
                + "\n"
                + "            app.controller('sliderDemoCtrl', function($scope, $log, colorpicker) {\n"
                + "                $scope.demoVals = {\n"
                + "                    sliderExample3: 0,\n"
                + "                };\n"
                + "            });\n"
                + "\n"
                + "            setInterval(function(){\n"
                + "                $('#conteudo').load('infoAdmin.html');\n"
                + "            }, 100);\n"
                + "        </script>\n"
                + "        <style>\n"
                + "            body {\n"
                + "                font-family: 'Annie Use Your Telescope';\n"
                + "                font-size: 25px;\n"
                + "            }\n"
                + "            .ui-slider-tick {\n"
                + "                position: absolute;\n"
                + "                background-color: #aaa;\n"
                + "                width: 2px;\n"
                + "                height: 8px;\n"
                + "                top: 16px;\n"
                + "            }\n"
                + "\n"
                + "            .ui-slider {\n"
                + "                position: relative;\n"
                + "            }\n"
                + "\n"
                + "            .ui-slider-out-of-bounds {\n"
                + "                position: absolute;\n"
                + "                background-color: #EEE;\n"
                + "                height: 100%;\n"
                + "            }\n"
                + "\n"
                + "            .ui-slider-tip {\n"
                + "                position: absolute;\n"
                + "                top: 2px;\n"
                + "                left: 0px;\n"
                + "                width: 21px;\n"
                + "                text-align: center;\n"
                + "                font-size: 0.8em;\n"
                + "            }\n"
                + "\n"
                + "            .sliderExample {\n"
                + "                width: 640px;\n"
                + "                padding: 2em;\n"
                + "            }\n"
                + "            .sliderExample > div {\n"
                + "                margin: 1em 0;\n"
                + "            }\n"
                + "            .vertical-container {\n"
                + "                width: 15%;\n"
                + "                display: inline-block;\n"
                + "            }\n"
                + "            #swatch {\n"
                + "                width: 100px;\n"
                + "                height: 100px;\n"
                + "                border: 1px solid black;\n"
                + "            }\n"
                + "            #red .ui-slider-range { background: #ef2929; }\n"
                + "            #red .ui-slider-handle { border-color: #ef2929; }\n"
                + "            #green .ui-slider-range { background: #8ae234; }\n"
                + "            #green .ui-slider-handle { border-color: #8ae234; }\n"
                + "            #blue .ui-slider-range { background: #729fcf; }\n"
                + "            #blue .ui-slider-handle { border-color: #729fcf; }\n"
                + "        </style>\n"
                + "    </head>\n"
                + "    <body ng-controller=\"sliderDemoCtrl\" class=\"container\">\n"
                + "        <h1 align=\"center\"> Information Web Server </h1>\n"
                + "        <hr> </hr>\n"
                + "        <div class=\"sliderExample\"><a name=\"ex3\"></a>\n"
                + "            <p> Ajuste o tempo de atualização desejada </p>\n"
                + "            <div ui-slider min=\"0\" max=\"60\" ng-model=\"demoVals.sliderExample3\"></div>\n"
                + "            <input type=\"text\" ng-model=\"demoVals.sliderExample3\" disabled=\"disabled\"> (em segundos)</input>\n"
                + "            <div id=\"conteudo\"></div>\n"
                + "        </div>\n"
                + "    </body>\n"
                + "</html>"
                + "</>";
        return content;
    }

    /**
     * O método generateVirtualHtml(String path) gera um arquivo HTML que será retornado ao
     * cliente.
     *
     * @param path String com o nome do arquivo idêntico nome do virtual path.
     * @return void, ou seja, nada.
     */
    public void generateVirtualHtml(String path) {
        try (BufferedWriter f = new BufferedWriter(new FileWriter(path))) {
            /* Preenchendo o arquivo HTML do virtual path */
            f.write(generateAdminHtml());
            f.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * O método setFilesRequired(String file, String nameDirectory, BufferedReader buffer) adiciona o nome da página, o nome do
     * diretório caso seja um diretório e o buffer que foi enviado pelo cliente,
     * todos esses campos são adicionados na lista que contém todos os arquivos
     * que foram solicitados.
     *
     * @param file String com o nome do arquivo que foi solicitado pelo cliente.
     * @param nameDirectory String com o nome o diretório caso a página que
     * tenha sido solicitada foi um diretório.
     * @param buffer BufferedReader com o buffer que foi enviado pelo cliente.
     * @return void, ou seja, nada.
     */
    public void setFilesRequired(String file, String nameDirectory, BufferedReader buffer) {
        /* Instanciando uma classe */
        InfoRequest i = new InfoRequest();
        i.setPage(file);
        i.setNameDirectory(nameDirectory);
        i.setBuffer(buffer);

        /* Adicionando em uma lista */
        filesRequired.add(i);
    }

    /**
     * O método getFilesRequiredSize() retorna o tamanho da lista que guarda o
     * nome da página, buffer que lhe foi enviado pelo cliente ou nome do
     * diretório, caso seja um diretório.
     *
     * @return String com o nome da última página presente na lista.
     */
    public int getFilesRequiredSize() {
        return filesRequired.size();
    }

    /**
     * O método getLastFilesRequired() retorna o nome da última página que está
     * presente na lista que guarda o nome da página, buffer que lhe foi enviado
     * pelo cliente ou nome do diretório, caso seja um diretório.
     *
     * @return String com o nome da última página presente na lista.
     */
    public String getLastFilesRequired() {
        return filesRequired.get(filesRequired.size() - 1).getPage();
    }

    /**
     * O método getLastBuffer() retorna o último buffer que está presente na
     * lista que guarda o nome da página, buffer que lhe foi enviado pelo
     * cliente ou nome do diretório, caso seja um diretório.
     *
     * @return BufferedReader com o último buffer presente na lista.
     */
    public BufferedReader getLastBuffer() {
        return filesRequired.get(filesRequired.size() - 1).getBuffer();
    }

    /**
     * O método sortNameFile(List<Arquivo> listFiles) ordena a lista dos arquivos do diretório baseado
     * no nomes dos arquivos.
     *
     * @param listFiles Lista com o nome de todos os arquivos presente naquele
     * diretório.
     */
    public void sortNameFile(List<Arquivo> listFiles) {
        Collections.sort(listFiles, new Comparator<Arquivo>() {
            @Override
            public int compare(Arquivo o1, Arquivo o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
    }

    /**
     * O método sortLastModifiedFile(List<Arquivo> listFiles) ordena a lista dos arquivos do diretório
     * baseado na última modificação.
     *
     * @param listFiles Lista com o nome de todos os arquivos presente naquele
     * diretório.
     */
    public void sortLastModifiedFile(List<Arquivo> listFiles) {
        Collections.sort(listFiles, new Comparator<Arquivo>() {
            @Override
            public int compare(Arquivo o1, Arquivo o2) {
                return o1.getLastModified().compareTo(o2.getLastModified());
            }
        });

    }

    /**
     * O método sortSizeFile(List<Arquivo> listFiles) ordena a lista dos arquivos do diretório baseado
     * no tamanho do arquivo.
     *
     * @param listFiles Lista com o nome de todos os arquivos presente naquele
     * diretório.
     */
    public void sortSizeFile(List<Arquivo> listFiles) {
        Collections.sort(listFiles, new Comparator<Arquivo>() {
            @Override
            public int compare(Arquivo o1, Arquivo o2) {
                return Integer.compare(Integer.parseInt(o1.getSize()), Integer.parseInt(o2.getSize()));
            }
        });
    }

    /**
     * O método sortNameFile(List<Arquivo> listFiles) ordena de maneira inversa a lista dos arquivos do
     * diretório baseado nomes dos arquivos.
     *
     * @param listFiles Lista com o nome de todos os arquivos presente naquele
     * diretório.
     */
    public void sortReverseNameFile(List<Arquivo> listFiles) {
        Collections.sort(listFiles, new Comparator<Arquivo>() {
            @Override
            public int compare(Arquivo o1, Arquivo o2) {
                return o1.getName().compareTo(o2.getName());
            }
        }.reversed());
    }

    /**
     * O método sortReverseLastModifiedFile(List<Arquivo> listFiles) ordena de maneira inversa a lista
     * dos arquivos do diretório baseado na última modificação.
     *
     * @param listFiles Lista com o nome de todos os arquivos presente naquele
     * diretório.
     */
    public void sortReverseLastModifiedFile(List<Arquivo> listFiles) {
        Collections.sort(listFiles, new Comparator<Arquivo>() {
            @Override
            public int compare(Arquivo o1, Arquivo o2) {
                return o1.getLastModified().compareTo(o2.getLastModified());
            }
        }.reversed());
    }

    /**
     * O método sortReverseSizeFile(List<Arquivo> listFiles) ordena de maneira inversa a lista dos
     * arquivos do diretório baseado no tamanho do arquivo.
     *
     * @param listFiles Lista com o nome de todos os arquivos presente naquele
     * diretório.
     */
    public void sortReverseSizeFile(List<Arquivo> listFiles) {
        Collections.sort(listFiles, new Comparator<Arquivo>() {
            @Override
            public int compare(Arquivo o1, Arquivo o2) {
                return Integer.compare(Integer.parseInt(o1.getSize()), Integer.parseInt(o2.getSize()));
            }
        }.reversed());
    }

    /**
     * O método getDirectory(String path) percorre o path do arquivo, abre o arquivo e
     * retorna o diretório que está sendo exibido nesse arquivo.
     *
     * @param path String com o nome do arquivo onde será extraído o diretório.
     * @return directory[3] retorna o diretório que está sendo mostrado no
     * arquivo.
     * @throws java.io.FileNotFoundException
     * @throws java.IOException
     */
    public String getDirectory(String path) throws FileNotFoundException, IOException {
        File file = new File(path);
        String[] directory = null;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            /* Percorre o arquivo */
            while ((line = br.readLine()) != null) {
                /* Pega o título e retorna o nome do diretório que tá sendo exibido */
                if (line.contains("<title>")) {
                    directory = line.split(" ");
                    break;
                }
            }
        }
        return directory[3];
    }
}
